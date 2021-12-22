/**
 * Copyright (c) 2022 TerraFrame, Inc. All rights reserved.
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
package org.commongeoregistry.adapter.action.tree;

import org.commongeoregistry.adapter.action.AbstractActionDTO;
import org.commongeoregistry.adapter.constants.RegistryUrls;

import com.google.gson.JsonObject;

public class RemoveChildActionDTO extends AbstractActionDTO
{
    private String childId;
    
    private String childTypeCode;

    private String parentId;

    private String hierarchyCode;
    
    private String parentTypeCode;
    
    public RemoveChildActionDTO()
    {
      super(RegistryUrls.GEO_OBJECT_REMOVE_CHILD);
    }
    
    @Override
    protected void buildJson(JsonObject json)
    {
      super.buildJson(json);
      
      json.addProperty(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_CHILDID, this.childId);
      json.addProperty(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_CHILD_TYPE_CODE, this.childTypeCode);
      json.addProperty(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_PARENTID, this.parentId);
      json.addProperty(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_PARENT_TYPE_CODE, this.parentTypeCode);
      json.addProperty(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_HIERARCHY_CODE, this.hierarchyCode);
    }
    
    @Override
    protected void buildFromJson(JsonObject json)
    {
      super.buildFromJson(json);
      
      this.childId = json.get(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_CHILDID).getAsString();
      this.childTypeCode = json.get(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_CHILD_TYPE_CODE).getAsString();
      this.parentId = json.get(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_PARENTID).getAsString();
      this.parentTypeCode = json.get(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_PARENT_TYPE_CODE).getAsString();
      this.hierarchyCode = json.get(RegistryUrls.GEO_OBJECT_REMOVE_CHILD_PARAM_HIERARCHY_CODE).getAsString();
    }
    
    public void setChildId(String childId)
    {
      this.childId = childId;
    }

    public void setChildTypeCode(String childTypeCode)
    {
      this.childTypeCode = childTypeCode;
    }

    public void setParentId(String parentId)
    {
      this.parentId = parentId;
    }

    public void setHierarchyCode(String hierarchyCode)
    {
      this.hierarchyCode = hierarchyCode;
    }

    public void setParentTypeCode(String parentTypeCode)
    {
      this.parentTypeCode = parentTypeCode;
    }

    public String getChildId()
    {
      return childId;
    }
    
    public String getChildTypeCode()
    {
      return childTypeCode;
    }

    public String getParentId()
    {
      return parentId;
    }
    
    public String getParentTypeCode()
    {
      return parentTypeCode;
    }

    public String getHierarchyCode()
    {
      return hierarchyCode;
    }
}
