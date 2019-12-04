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
package org.commongeoregistry.adapter.constants;

public class RegistryUrls
{
  public static final String REGISTRY_CONTROLLER_URL  = "cgr";
  
  /**
   * Geo Objects
   */
  
  public static final String GEO_OBJECT_GET            = "geoobject/get";
  public static final String GEO_OBJECT_GET_PARAM_ID   = "id";
  public static final String GEO_OBJECT_GET_PARAM_TYPE_CODE = "typeCode";
  
  public static final String GEO_OBJECT_GET_CODE      = "geoobject/get-code";
  public static final String GEO_OBJECT_GET_CODE_PARAM_CODE = "code";
  public static final String GEO_OBJECT_GET_CODE_PARAM_TYPE_CODE = "typeCode";
  
  public static final String GEO_OBJECT_GET_TIME_CODE  = "geoobject/getOverTimeByCode";

  public static final String GEO_OBJECT_UPDATE        = "geoobject/update";
  public static final String GEO_OBJECT_UPDATE_PARAM_GEOOBJECT = "geoObject";
  
  public static final String GEO_OBJECT_CREATE        = "geoobject/create";
  public static final String GEO_OBJECT_CREATE_PARAM_GEOOBJECT = "geoObject";
  
  public static final String GEO_OBJECT_NEW_INSTANCE  = "geoobject/newGeoObjectInstance";
  public static final String GEO_OBJECT_NEW_INSTANCE_PARAM_TYPE_CODE = "typeCode";
  
  public static final String GEO_OBJECT_GET_CHILDREN = "geoobject/getchildren";
  public static final String GEO_OBJECT_GET_CHILDREN_PARAM_PARENTID = "parentId";
  public static final String GEO_OBJECT_GET_CHILDREN_PARAM_PARENT_TYPE_CODE = "parentTypeCode";
  public static final String GEO_OBJECT_GET_CHILDREN_PARAM_CHILDREN_TYPES = "childrenType";
  public static final String GEO_OBJECT_GET_CHILDREN_PARAM_RECURSIVE = "recursive";

  public static final String GEO_OBJECT_GET_PARENTS   = "geoobject/get-parent-geoobjects";
  public static final String GEO_OBJECT_GET_PARENTS_PARAM_CHILDID = "childId";
  public static final String GEO_OBJECT_GET_PARENTS_PARAM_CHILD_TYPE_CODE = "childTypeCode";
  public static final String GEO_OBJECT_GET_PARENTS_PARAM_PARENT_TYPES = "parentTypes";
  public static final String GEO_OBJECT_GET_PARENTS_PARAM_RECURSIVE = "recursive";
  
  public static final String GEO_OBJECT_ADD_CHILD     = "geoobject/addchild";
  public static final String GEO_OBJECT_ADD_CHILD_PARAM_PARENTID = "parentId";
  public static final String GEO_OBJECT_ADD_CHILD_PARAM_PARENT_TYPE_CODE = "parentTypeCode";
  public static final String GEO_OBJECT_ADD_CHILD_PARAM_CHILDID = "childId";
  public static final String GEO_OBJECT_ADD_CHILD_PARAM_CHILD_TYPE_CODE = "childTypeCode";
  public static final String GEO_OBJECT_ADD_CHILD_PARAM_HIERARCHY_CODE = "hierarchyCode";
  
  public static final String GEO_OBJECT_REMOVE_CHILD     = "geoobject/removechild";
  public static final String GEO_OBJECT_REMOVE_CHILD_PARAM_PARENTID = "parentId";
  public static final String GEO_OBJECT_REMOVE_CHILD_PARAM_PARENT_TYPE_CODE = "parentTypeCode";
  public static final String GEO_OBJECT_REMOVE_CHILD_PARAM_CHILDID = "childId";
  public static final String GEO_OBJECT_REMOVE_CHILD_PARAM_CHILD_TYPE_CODE = "childTypeCode";
  public static final String GEO_OBJECT_REMOVE_CHILD_PARAM_HIERARCHY_CODE = "hierarchyCode";

  public static final String GEO_OBJECT_GET_UIDS      = "geoobject/get-uids";
  public static final String GEO_OBJECT_GET_UIDS_PARAM_AMOUNT = "amount";
  
  public static final String GEO_OBJECT_GET_ATTRIBUTE_VERSIONS = "geoobject/getAttributeVersions";
  public static final String GEO_OBJECT_GET_ATTRIBUTE_VERSIONS_PARAM_ID = "attributeId";
  
  public static final String GEO_OBJECT_SET_ATTRIBUTE_VERSIONS = "geoobject/setAttributeVersions";
  public static final String GEO_OBJECT_SET_ATTRIBUTE_VERSIONS_PARAM_ID = "attributeId";
  public static final String GEO_OBJECT_SET_ATTRIBUTE_VERSIONS_PARAM_COLLECTION = "collection";
  
  /**
   * Geo Object Types
   */
  
  public static final String GEO_OBJECT_TYPE_GET_ALL  = "geoobjecttype/get-all";
  public static final String GEO_OBJECT_TYPE_GET_ALL_PARAM_TYPES  = "types";
  
  public static final String GEO_OBJECT_TYPE_CREATE   = "geoobjecttype/create";
  public static final String GEO_OBJECT_TYPE_CREATE_PARAM_GOT   = "gtJSON";
  
  public static final String GEO_OBJECT_TYPE_UPDATE   = "geoobjecttype/update";
  
  public static final String GEO_OBJECT_TYPE_DELETE   = "geoobjecttype/delete";
  
  public static final String GEO_OBJECT_TYPE_ADD_ATTRIBUTE        = "geoobjecttype/addattribute";
  public static final String GEO_OBJECT_TYPE_ADD_ATTRIBUTE_PARAM = "geoObjTypeId";
  public static final String GEO_OBJECT_TYPE_ADD_ATTRIBUTE_TYPE_PARAM = "attributeType";
  
  public static final String GEO_OBJECT_TYPE_UPDATE_ATTRIBUTE        = "geoobjecttype/updateattribute";
  public static final String GEO_OBJECT_TYPE_UPDATE_ATTRIBUTE_PARAM = "geoObjTypeId";
  public static final String GEO_OBJECT_TYPE_UPDATE_ATTRIBUTE_TYPE_PARAM = "attributeType";
  
  public static final String GEO_OBJECT_TYPE_DELETE_ATTRIBUTE        = "geoobjecttype/deleteattribute";
  public static final String GEO_OBJECT_TYPE_DELETE_ATTRIBUTE_PARAM = "geoObjTypeId";
  public static final String GEO_OBJECT_TYPE_DELETE_ATTRIBUTE_TYPE_PARAM = "attributeName";
  
  public static final String GEO_OBJECT_TYPE_ADD_TERM 	= "geoobjecttype/addterm";
  public static final String GEO_OBJECT_TYPE_ADD_TERM_PARENT_PARAM 	= "parentTermCode";
  public static final String GEO_OBJECT_TYPE_ADD_TERM_PARAM 	= "termJSON";
  
  public static final String GEO_OBJECT_TYPE_UPDATE_TERM 	= "geoobjecttype/updateterm";
  public static final String GEO_OBJECT_TYPE_UPDATE_TERM_PARAM 	= "termJSON";
  
  public static final String GEO_OBJECT_TYPE_DELETE_TERM 	= "geoobjecttype/deleteterm";
  public static final String GEO_OBJECT_TYPE_DELETE_TERM_PARAM 	= "termCode";
  
  /**
   * Hierarchy Types
   */
  
  public static final String HIERARCHY_TYPE_GET_ALL   = "hierarchytype/get-all";
  
  public static final String HIERARCHY_TYPE_CREATE   = "hierarchytype/create";
  
  public static final String HIERARCHY_TYPE_UPDATE   = "hierarchytype/update";
  
  public static final String HIERARCHY_TYPE_DELETE   = "hierarchytype/delete";
  
  public static final String HIERARCHY_TYPE_ADD      = "hierarchytype/add";
  
  public static final String HIERARCHY_TYPE_REMOVE   = "hierarchytype/remove";
  
  
  /**
   * Miscellaneous
   */
  
  public static final String SUBMIT_CHANGE_REQUEST         = "submitChangeRequest";
  public static final String SUBMIT_CHANGE_REQUEST_PARAM_ACTIONS = "actions";
  public static final String HIERARCHY_TERM_SEARCH	 = "search";
}
