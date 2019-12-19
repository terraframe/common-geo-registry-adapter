package org.commongeoregistry.adapter.dataaccess;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.commongeoregistry.adapter.Optional;
import org.commongeoregistry.adapter.RegistryAdapter;
import org.commongeoregistry.adapter.RequiredParameterException;
import org.commongeoregistry.adapter.Term;
import org.commongeoregistry.adapter.constants.DefaultAttribute;
import org.commongeoregistry.adapter.metadata.AttributeGeometryType;
import org.commongeoregistry.adapter.metadata.AttributeTermType;
import org.commongeoregistry.adapter.metadata.AttributeType;
import org.commongeoregistry.adapter.metadata.CustomSerializer;
import org.commongeoregistry.adapter.metadata.DefaultSerializer;
import org.commongeoregistry.adapter.metadata.GeoObjectType;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vividsolutions.jts.geom.Geometry;

public class GeoObjectOverTime implements Serializable
{

  private static final long serialVersionUID = 6218261169426542019L;
  
  public static final String     JSON_ATTRIBUTES  = "attributes";
  
  private GeoObjectType          geoObjectType;
  
  /**
   * For attributes that do change over time, they will be stored here.
   */
  private Map<String, ValueOverTimeCollectionDTO> votAttributeMap;
  
  /**
   * Not all attributes are stored with change-over-time properties. You can check the AttributeType
   * to see if the attribute changes over time.
   */
  private Map<String, Attribute> attributeMap;
  
  private AttributeGeometryType geometryAttributeType;
  
  public GeoObjectOverTime(GeoObjectType geoObjectType, Map<String, ValueOverTimeCollectionDTO> votAttributeMap, Map<String, Attribute> attributeMap)
  {
    this.geoObjectType = geoObjectType;
    this.votAttributeMap = votAttributeMap;
    this.attributeMap = attributeMap;
    geometryAttributeType = (AttributeGeometryType) DefaultAttribute.GEOMETRY.createAttributeType();
    
    this.setValue(DefaultAttribute.TYPE.getName(), this.geoObjectType.getCode());
  }
  
  public static Map<String, ValueOverTimeCollectionDTO> buildVotAttributeMap(GeoObjectType geoObjectType)
  {
    Map<String, AttributeType> attributeTypeMap = geoObjectType.getAttributeMap();

    Map<String, ValueOverTimeCollectionDTO> attributeMap = new ConcurrentHashMap<String, ValueOverTimeCollectionDTO>();

    for (AttributeType attributeType : attributeTypeMap.values())
    {
      if (attributeType.isChangeOverTime())
      {
        ValueOverTimeCollectionDTO votc = new ValueOverTimeCollectionDTO(attributeType);
  
        attributeMap.put(attributeType.getName(), votc);
      }
    }
    
    AttributeGeometryType geometry = (AttributeGeometryType) DefaultAttribute.GEOMETRY.createAttributeType();
    ValueOverTimeCollectionDTO votc = new ValueOverTimeCollectionDTO(geometry);
    attributeMap.put(geometry.getName(), votc);

    return attributeMap;
  }
  
  public static Map<String, Attribute> buildAttributeMap(GeoObjectType geoObjectType)
  {
    Map<String, AttributeType> attributeTypeMap = geoObjectType.getAttributeMap();

    Map<String, Attribute> attributeMap = new ConcurrentHashMap<String, Attribute>();

    for (AttributeType attributeType : attributeTypeMap.values())
    {
      if (!attributeType.isChangeOverTime())
      {
        Attribute attribute = Attribute.attributeFactory(attributeType);

        attributeMap.put(attribute.getName(), attribute);
      }
    }

    return attributeMap;
  }
  
  public GeoObjectType getType()
  {
    return this.geoObjectType;
  }
  
  
  /**
   * Returns the Attribute at the exact start date. If date is null,
   * it is assumed to be the latest date at which data is available (infinity).
   * If no values exist, one will be created. If no values exist and the date is null,
   * then a value will be created with the current date.
   * 
   * @param date
   * @return
   */
  public Attribute getOrCreateAttribute(String key, Date startDate)
  {
    return this.votAttributeMap.get(key).getOrCreateAttribute(startDate);
  }
  
  /**
   * Returns the attribute which represents the given day. If no start or end date exactly
   * matches this day, then the attribute which spans the date range which this date falls
   * within will be returned. If the provided date is null, the date is assumed to be infinity,
   * in which case the latest value will be returned. This method may return null if the provided
   * date occurs before all recorded data.
   * 
   * @param key
   * @param date
   * @return
   */
  public Attribute getAttributeOnDate(String key, Date date)
  {
    return this.votAttributeMap.get(key).getAttributeOnDate(date);
  }
  
  /**
   * Sets the WKT geometry at the exact start date. If date is null, it will be set to today's date.
   * If no value exists at the exact start date, one will be created. The end date will automatically
   * span the range to the next available value in the system, or infinity if one does not exist.
   * 
   * @param date
   * @return
   */
  public void setWKTGeometry(String wkt, Date startDate)
  {
    ((AttributeGeometry) this.votAttributeMap.get(DefaultAttribute.GEOMETRY.getName()).getOrCreateAttribute(startDate)).setWKTGeometry(wkt);
  }
  
  public ValueOverTimeCollectionDTO getAllValues(String attributeName)
  {
    return this.votAttributeMap.get(attributeName);
  }
  
  /**
   * Returns the value of the non-change-over-time attribute with the given name.
   * 
   * @pre attribute with the given name is defined on the {@link GeoObjectType}
   *      that defines this {@link GeoObject}.
   * 
   * @param attributeName
   * 
   * @return value of the attribute with the given name.
   */
  public Object getValue(String attributeName)
  {
    if (this.attributeMap.containsKey(attributeName))
    {
      return this.attributeMap.get(attributeName).getValue();
    }
    else if (this.votAttributeMap.containsKey(attributeName))
    {
      return this.votAttributeMap.get(attributeName).getValueOnDate(null);
    }
    else
    {
      throw new RuntimeException("Attribute not found [" + attributeName + "]."); // TODO : Better error handling
    }
  }
  
  /**
   * Returns the value which represents the given day. If no start or end date exactly
   * matches this day, then the value which spans the date range which this date falls
   * within will be returned. If the provided date is null, the date is assumed to be infinity,
   * in which case the latest value will be returned. This method may return null if the provided
   * date occurs before all recorded data.
   * 
   * @pre attribute with the given name is defined on the {@link GeoObjectType}
   *      that defines this {@link GeoObject}.
   * 
   * @param attributeName
   * 
   * @return value of the attribute with the given name.
   */
  public Object getValue(String attributeName, Date date)
  {
    return this.votAttributeMap.get(attributeName).getValueOnDate(date);
  }
  
  /**
   * Sets the value of the non-change-over-time attribute.
   * 
   * @param attributeName
   * @param _value
   */
  public void setValue(String attributeName, Object _value)
  {
    Optional<AttributeType> optional = this.getType().getAttribute(attributeName);
    
    if (optional.isPresent())
    {
      optional.get().validate(_value);
    }
    
    if (this.attributeMap.containsKey(attributeName))
    {
      this.attributeMap.get(attributeName).setValue(_value);
    }
    else if (this.votAttributeMap.containsKey(attributeName))
    {
      this.votAttributeMap.get(attributeName).setValue(_value, null);
    }
    else
    {
      throw new RuntimeException("Attribute not found [" + attributeName + "]."); // TODO : Better error handling
    }
  }
  
  /**
   * Sets the value of the change-over-time attribute. If endDate is null then it is assumed to
   * expand as far as possible into the future. If startDate is null then it will grab the latest
   * available value and set it. If one does not exist one will be created with today's date.
   * 
   * @throws {@link RequiredParamterException} if startDate is missing
   * @param attributeName
   * @param _value
   */
  public void setValue(String attributeName, Object _value, Date startDate, Date endDate)
  {
    ValueOverTimeCollectionDTO votc = this.votAttributeMap.get(attributeName);
    
    if (attributeName.equals(DefaultAttribute.GEOMETRY.getName()))
    {
      geometryAttributeType.validate(_value);
    }
    else
    {
      Optional<AttributeType> optional = this.getType().getAttribute(attributeName);
      
      if (optional.isPresent())
      {
        optional.get().validate(_value);
      }
    }
    
    ValueOverTimeDTO vot = votc.getOrCreate(startDate);
    vot.setEndDate(endDate);
    vot.setValue(_value);
  }

  /**
   * Returns the geometry of this {@link GeoObject}
   * 
   * @return the geometry of this {@link GeoObject}
   */
  public Geometry getGeometry(Date date)
  {
    return (Geometry) this.getValue(DefaultAttribute.GEOMETRY.getName(), date);
  }

  /**
   * Set the {@link Geometry} on this {@link GeoObject}
   * 
   * @param geometry
   */
  public void setGeometry(Geometry geometry, Date startDate, Date endDate)
  {
    this.setValue(DefaultAttribute.GEOMETRY.getName(), geometry, startDate, endDate);
  }
  
  /**
   * Sets the code of this {@link GeoObject}.
   * 
   * @param code
   */
  public void setCode(String code)
  {
    this.setValue(DefaultAttribute.CODE.getName(), code);
  }

  /**
   * Returns the code id of this {@link GeoObject}
   * 
   * @return the code id of this {@link GeoObject}
   */
  public String getCode()
  {
    return (String) this.getValue(DefaultAttribute.CODE.getName());
  }
  
  /**
   * Sets the display label of this {@link GeoObject}. If endDate is null then it is assumed to
   * expand as far as possible into the future. If startDate is null then it will grab the latest
   * available value and set it. If one does not exist one will be created.
   * 
   * @param label
   * @param startDate
   * @param endDate
   */
  public void setDisplayLabel(LocalizedValue label, Date startDate, Date endDate)
  {
    this.setValue(DefaultAttribute.DISPLAY_LABEL.getName(), label, startDate, endDate);
  }

  /**
   * Returns the display label of this {@link GeoObjectOverTime}. If date is null
   * it is assumed to be the latest date at which data is available (infinity).
   * 
   * @return the display label of this {@link GeoObjectOverTime}
   */
  public LocalizedValue getDisplayLabel(Date startDate)
  {
    return (LocalizedValue) this.getValue(DefaultAttribute.DISPLAY_LABEL.getName(), startDate);
  }

  /**
   * Sets the UID of this {@link GeoObject}.
   * 
   * @param uid
   */
  public void setUid(String uid)
  {
    this.setValue(DefaultAttribute.UID.getName(), uid);
  }

  /**
   * Returns the UID of this {@link GeoObject}.
   * 
   * @return
   */
  public String getUid()
  {
    return (String) this.getValue(DefaultAttribute.UID.getName());
  }
  
  /**
   * Returns the status code
   * 
   * @return
   */
  public Term getStatus(Date date)
  {
    Term term = null;

    Optional<AttributeType> optionalAttributeType = this.getType().getAttribute(DefaultAttribute.STATUS.getName());

    if (optionalAttributeType.isPresent())
    {
      AttributeTermType attributeTermType = (AttributeTermType) optionalAttributeType.get();

      @SuppressWarnings("unchecked")
      Iterator<String> statusIt = ( (Iterator<String>) this.getValue(DefaultAttribute.STATUS.getName(), date) );
      
      if (statusIt.hasNext())
      {
        String termCode = statusIt.next();
        Optional<Term> optionalTerm = attributeTermType.getTermByCode(termCode);
  
        if (optionalTerm.isPresent())
        {
          term = optionalTerm.get();
        }
      }
    }

    return term;
  }

  public void setStatus(Term status, Date startDate, Date endDate)
  {
    this.setValue(DefaultAttribute.STATUS.getName(), status.getCode(), startDate, endDate);
  }

  public void setStatus(String statusCode, Date startDate, Date endDate)
  {
    this.setValue(DefaultAttribute.STATUS.getName(), statusCode, startDate, endDate);
  }
  
  /**
   * Creates a {@link GeoObjectOverTime} from the given JSON.
   * 
   * @pre assumes the attributes on the JSON are valid attributes defined by the
   *      {@link GeoObjectType}
   * 
   * @param _registry
   * @param _sJson
   * 
   * @return {@link GeoObjectOverTime} from the given JSON.
   */
  public static GeoObjectOverTime fromJSON(RegistryAdapter registry, String sJson)
  {
    JsonParser parser = new JsonParser();

    JsonObject joGO = parser.parse(sJson).getAsJsonObject();
    JsonObject joAttrs = joGO.getAsJsonObject(JSON_ATTRIBUTES);
    
    String type = joAttrs.get(DefaultAttribute.TYPE.getName()).getAsString();

    GeoObjectOverTime geoObj;
    if (joAttrs.has("uid"))
    {
      geoObj = registry.newGeoObjectOverTimeInstance(type, false);
    }
    else
    {
      geoObj = registry.newGeoObjectOverTimeInstance(type, true);
    }

    for (String key : geoObj.votAttributeMap.keySet())
    {
      ValueOverTimeCollectionDTO votc = geoObj.votAttributeMap.get(key);
      votc.clear();

      if (joAttrs.has(key) && !joAttrs.get(key).isJsonNull())
      {
        JsonObject attributeOverTime = joAttrs.get(key).getAsJsonObject();
        
        JsonArray jaValues = attributeOverTime.get("values").getAsJsonArray();
        
        for (int i = 0; i < jaValues.size(); ++i)
        {
          ValueOverTimeDTO vot = ValueOverTimeDTO.fromJSON(jaValues.get(i).toString(), votc, registry);
          
          votc.add(vot);
        }
      }
    }
    
    for (String key : geoObj.attributeMap.keySet())
    {
      Attribute attr = geoObj.attributeMap.get(key);

      if (joAttrs.has(key) && !joAttrs.get(key).isJsonNull())
      {
        attr.fromJSON(joAttrs.get(key), registry);
      }
    }
    
    return geoObj;
  }

  public JsonObject toJSON()
  {
    return toJSON(new DefaultSerializer());
  }

  public JsonObject toJSON(CustomSerializer serializer)
  {
    JsonObject jsonObj = new JsonObject();
    
    JsonObject attrs = new JsonObject();
    for (String key : this.votAttributeMap.keySet())
    {
      ValueOverTimeCollectionDTO votc = this.votAttributeMap.get(key);
      AttributeType type = votc.getAttributeType();
      
      JsonObject attributeOverTime = new JsonObject();
      attributeOverTime.addProperty("name", type.getName());
      attributeOverTime.addProperty("type", type.getType());
      
      JsonArray values = new JsonArray();
      
      for (ValueOverTimeDTO vot : votc)
      {
        values.add(vot.toJSON(serializer));
      }
      
      attributeOverTime.add("values", values);
      
      attrs.add(type.getName(), attributeOverTime);
    }
    
    for (String key : this.attributeMap.keySet())
    {
      Attribute attr = this.attributeMap.get(key);
      
      JsonElement value = attr.toJSON(serializer);
      if (!value.isJsonNull())
      {
        attrs.add(attr.getName(), value);
      }
    }

    jsonObj.add(JSON_ATTRIBUTES, attrs);

    return jsonObj;
  }
  
}
