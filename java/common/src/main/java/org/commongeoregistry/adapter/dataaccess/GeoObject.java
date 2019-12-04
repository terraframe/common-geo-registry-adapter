/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Common Geo Registry Adapter(tm).
 *
 * Common Geo Registry Adapter(tm) is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Common Geo Registry Adapter(tm) is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with Common Geo Registry Adapter(tm).  If not, see <http://www.gnu.org/licenses/>.
 */
package org.commongeoregistry.adapter.dataaccess;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.commongeoregistry.adapter.Optional;
import org.commongeoregistry.adapter.RegistryAdapter;
import org.commongeoregistry.adapter.Term;
import org.commongeoregistry.adapter.constants.DefaultAttribute;
import org.commongeoregistry.adapter.constants.GeometryType;
import org.commongeoregistry.adapter.metadata.AttributeGeometryType;
import org.commongeoregistry.adapter.metadata.AttributeTermType;
import org.commongeoregistry.adapter.metadata.AttributeType;
import org.commongeoregistry.adapter.metadata.CustomSerializer;
import org.commongeoregistry.adapter.metadata.DefaultSerializer;
import org.commongeoregistry.adapter.metadata.GeoObjectType;
import org.wololo.jts2geojson.GeoJSONReader;
import org.wololo.jts2geojson.GeoJSONWriter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeoObject implements Serializable
{

  /**
   * 
   */
  private static final long      serialVersionUID = 7686140708200106783L;

  public static final String     UID              = DefaultAttribute.UID.getName();

  public static final String     CODE             = DefaultAttribute.CODE.getName();

  public static final String     DISPLAY_LABEL    = DefaultAttribute.DISPLAY_LABEL.getName();

  public static final String     JSON_PROPERTIES  = "properties";

  public static final String     JSON_TYPE        = "type";

  public static final String     JSON_GEOMETRY    = "geometry";

  public static final String     JSON_FEATURE     = "Feature";

  private GeoObjectType          geoObjectType;

  private GeometryType           geometryType;

  private Geometry               geometry;

  private Map<String, Attribute> attributeMap;

  /**
   * Use the factory method on the {@link RegistryAdapter} to create new
   * instances of a {@link GeoObject}
   * 
   * @param geoObjectType
   * @param geometryType
   * @param attributeMap
   */
  public GeoObject(GeoObjectType geoObjectType, GeometryType geometryType, Map<String, Attribute> attributeMap)
  {
    this.geoObjectType = geoObjectType;

    this.geometryType = geometryType;

    this.geometry = null;

    this.attributeMap = attributeMap;

    this.getAttribute(DefaultAttribute.TYPE.getName()).setValue(geoObjectType.getCode());
  }

  /**
   * Returns a map of {@link Attribute} objects for a {@link GeoObject} of the
   * given {@link GeoObjectType}.
   * 
   * @param geoObjectType
   * 
   * @return map of {@link Attribute} objects for a {@link GeoObject} of the
   *         given {@link GeoObjectType}.
   */
  public static Map<String, Attribute> buildAttributeMap(GeoObjectType geoObjectType)
  {
    Map<String, AttributeType> attributeTypeMap = geoObjectType.getAttributeMap();

    Map<String, Attribute> attributeMap = new ConcurrentHashMap<String, Attribute>();

    for (AttributeType attributeType : attributeTypeMap.values())
    {
      if (attributeType instanceof AttributeGeometryType)
      {
        continue;
      }
      
      Attribute attribute = Attribute.attributeFactory(attributeType);

      attributeMap.put(attribute.getName(), attribute);
    }

    return attributeMap;
  }

  /**
   * Returns a reference to the {@link GeoObjectType} that defines this
   * {@link GeoObject}.
   * 
   * @return a reference to the {@link GeoObjectType} that defines this
   *         {@link GeoObject}.
   */
  public GeoObjectType getType()
  {
    return this.geoObjectType;
  }

  /**
   * Returns the type of the geometry of this {@link GeoObject}
   * 
   * @return type of the geometry of this {@link GeoObject}
   */
  public GeometryType getGeometryType()
  {
    return this.geometryType;
  }

  /**
   * Returns the geometry of this {@link GeoObject}
   * 
   * @return the geometry of this {@link GeoObject}
   */
  public Geometry getGeometry()
  {
    return this.geometry;
  }

  /**
   * Set the {@link Geometry} on this {@link GeoObject}
   * 
   * @param geometry
   */
  public void setGeometry(Geometry geometry)
  {
    this.geometry = geometry;
  }

  /**
   * Set the WKT geometry on this Geometry Type.
   * 
   * @param wkt
   */
  public void setWKTGeometry(String wkt)
  {
    Geometry wktObj = null;
    WKTReader wktReader = new WKTReader();
    try
    {
      wktObj = wktReader.read(wkt);
    }
    catch (ParseException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    this.setGeometry(wktObj);
  }

  /**
   * Returns the value of the attribute with the given name.
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
    return this.attributeMap.get(attributeName).getValue();
  }

  /**
   * Sets the value of the {@link attribute} object with the given name.
   * 
   * @param attributeName
   * @param _value
   */
  public void setValue(String attributeName, Object _value)
  {
    Attribute attribute = this.attributeMap.get(attributeName);

    Optional<AttributeType> optional = this.getType().getAttribute(attributeName);

    if (optional.isPresent())
    {
      optional.get().validate(_value);
    }

    attribute.setValue(_value);
  }

  /**
   * Returns the {@link attribute} object with the given name.
   * 
   * @pre attribute with the given name is defined on the {@link GeoObjectType}
   *      that defines this {@link GeoObject}.
   * 
   * @param attributeName
   * 
   * @return
   */
  public Attribute getAttribute(String attributeName)
  {
    return this.attributeMap.get(attributeName);
  }

  /**
   * Sets the code of this {@link GeoObject}.
   * 
   * @param code
   */
  public void setCode(String code)
  {
    this.attributeMap.get(CODE).setValue(code);
  }

  /**
   * Returns the code id of this {@link GeoObject}
   * 
   * @return the code id of this {@link GeoObject}
   */
  public String getCode()
  {
    return (String) this.attributeMap.get(CODE).getValue();
  }

  /**
   * Sets the UID of this {@link GeoObject}.
   * 
   * @param uid
   */
  public void setUid(String uid)
  {
    this.attributeMap.get(UID).setValue(uid);
  }

  /**
   * Returns the UID of this {@link GeoObject}.
   * 
   * @return
   */
  public String getUid()
  {
    return (String) this.attributeMap.get(UID).getValue();
  }

  /**
   * Returns the localized
   * 
   * @return
   */
  public String getLocalizedDisplayLabel()
  {
    LocalizedValue value = this.getDisplayLabel();

    return value.getValue();
  }

  /**
   * Returns the localized
   * 
   * @return
   */
  public LocalizedValue getDisplayLabel()
  {
    AttributeLocal attribute = (AttributeLocal) this.attributeMap.get(DISPLAY_LABEL);
    LocalizedValue value = (LocalizedValue) attribute.getValue();

    return value;
  }

  public void setDisplayLabel(LocalizedValue _displayLabel)
  {
    AttributeLocal attribute = (AttributeLocal) this.attributeMap.get(DISPLAY_LABEL);
    attribute.setValue(_displayLabel);
  }

  public void setDisplayLabel(String _key, String _displayLabel)
  {
    AttributeLocal attribute = (AttributeLocal) this.attributeMap.get(DISPLAY_LABEL);
    attribute.setValue(_key, _displayLabel);
  }

  /**
   * Returns the status code
   * 
   * @return
   */
  public Term getStatus()
  {
    Term term = null;

    Optional<AttributeType> optionalAttributeType = this.getType().getAttribute(DefaultAttribute.STATUS.getName());

    if (optionalAttributeType.isPresent())
    {
      AttributeTermType attributeTermType = (AttributeTermType) optionalAttributeType.get();

      @SuppressWarnings("unchecked")
      String termCode = ( (Iterator<String>) this.getValue(DefaultAttribute.STATUS.getName()) ).next();
      Optional<Term> optionalTerm = attributeTermType.getTermByCode(termCode);

      if (optionalTerm.isPresent())
      {
        term = optionalTerm.get();
      }
    }

    return term;
  }

  public void setStatus(Term status)
  {
    this.getAttribute(DefaultAttribute.STATUS.getName()).setValue(status.getCode());
  }

  public void setStatus(String statusCode)
  {
    this.getAttribute(DefaultAttribute.STATUS.getName()).setValue(statusCode);
  }

  /**
   * Creates a {@link GeoObject} from the given JSON.
   * 
   * @pre assumes the attributes on the JSON are valid attributes defined by the
   *      {@link GeoObjectType}
   * 
   * @param _registry
   * @param _sJson
   * 
   * @return {@link GeoObject} from the given JSON.
   */
  public static GeoObject fromJSON(RegistryAdapter registry, String sJson)
  {
    JsonParser parser = new JsonParser();

    JsonObject oJson = parser.parse(sJson).getAsJsonObject();
    JsonObject oJsonProps = oJson.getAsJsonObject(JSON_PROPERTIES);

    GeoObject geoObj;
    if (oJsonProps.has("uid"))
    {
      geoObj = registry.newGeoObjectInstance(oJsonProps.get(JSON_TYPE).getAsString(), false);
    }
    else
    {
      geoObj = registry.newGeoObjectInstance(oJsonProps.get(JSON_TYPE).getAsString(), true);
    }

    JsonElement oGeom = oJson.get(JSON_GEOMETRY);
    if (oGeom != null)
    {
      GeoJSONReader reader = new GeoJSONReader();
      Geometry jtsGeom = reader.read(oGeom.toString());

      geoObj.setGeometry(jtsGeom);
    }

    for (String key : geoObj.attributeMap.keySet())
    {
      Attribute attr = geoObj.attributeMap.get(key);

      if (oJsonProps.has(key) && !oJsonProps.get(key).isJsonNull())
      {
        attr.fromJSON(oJsonProps.get(key), registry);
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

    // It's assumed that GeoObjects are simple features rather than
    // FeatureCollections.
    // Spec reference: https://tools.ietf.org/html/rfc7946#section-3.3
    jsonObj.addProperty(JSON_TYPE, JSON_FEATURE);

    if (this.getGeometry() != null)
    {
      GeoJSONWriter gw = new GeoJSONWriter();
      org.wololo.geojson.Geometry gJSON = gw.write(this.getGeometry());

      JsonParser parser = new JsonParser();
      JsonObject geomObj = parser.parse(gJSON.toString()).getAsJsonObject();

      jsonObj.add(JSON_GEOMETRY, geomObj);
    }

    JsonObject props = new JsonObject();
    for (String key : this.attributeMap.keySet())
    {
      Attribute attr = this.attributeMap.get(key);

      JsonElement value = attr.toJSON(serializer);
      if (!value.isJsonNull())
      {
        props.add(attr.getName(), value);
      }

      // if(attr instanceof AttributeTerm)
      // {
      // attrs.add(key, attr.toJSON());
      // }
      // else
      // {
      //
      // System.out.println(attr.toJSON());
      //
      // // TODO: All these attributes are required by the CGR spec. Adding an
      // // empty string is a temporary step for me to work on another area of
      // // the adapter. Ensure that Values are always present and handle
      // // NULLs as errors.
      // if(attr.getValue() == null )
      // {
      // attrs.addProperty(key, "");
      // }
      // else
      // {
      // attrs.addProperty(key, attr.getValue().toString() );
      // }
      // }

      // JsonParser attrParser = new JsonParser();
      // JsonObject geomObj =
      // attrParser.parse(attr.toJSON().toString()).getAsJsonObject();

    }

    jsonObj.add(JSON_PROPERTIES, props);

    return jsonObj;
  }

  public void printAttributes()
  {
    for (Attribute attribute : attributeMap.values())
    {
      System.out.println(attribute.toString());
    }

    System.out.println("Geometry: " + this.geometry);
  }
  
  @Override
  public boolean equals(Object obj)
  {
    if (!(obj instanceof GeoObject))
    {
      return false;
    }
    
    GeoObject go = (GeoObject) obj;
    
    return this.getCode().equals(go.getCode()) && this.getType().getCode().equals(go.getType().getCode());
  }
}
