package org.commongeoregistry.adapter.dataaccess;

import org.commongeoregistry.adapter.RegistryAdapter;
import org.commongeoregistry.adapter.metadata.AttributeFloatType;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AttributeFloat extends Attribute
{

  /**
   * 
   */
  private static final long serialVersionUID = 585645995864808480L;
  
  private Double floatValue;
  
  public AttributeFloat(String name)
  {
    super(name, AttributeFloatType.TYPE);
    
    this.floatValue = null;
  }
  
  @Override
  public void setValue(Object floatValue)
  {
    this.setFloat((Double)floatValue);
  }
  
  public void setFloat(Double floatValue)
  {
    this.floatValue = floatValue;
  }
  
  @Override
  public Double getValue()
  {
    return this.floatValue;
  }
  
  public void toJSON(JsonObject geoObjProps)
  {
    geoObjProps.addProperty(this.getName(), this.floatValue);
  }
  
  public void fromJSON(JsonElement jValue, RegistryAdapter registry)
  {
    this.setValue(jValue.getAsDouble());
  }


}
