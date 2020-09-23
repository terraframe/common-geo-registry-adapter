/**
 * Copyright (c) 2019 TerraFrame, Inc. All rights reserved.
 *
 * This file is part of Common Geo Registry Adapter(tm).
 *
 * Common Geo Registry Adapter(tm) is free software: you can redistribute it
 * and/or modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * Common Geo Registry Adapter(tm) is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Common Geo Registry Adapter(tm). If not, see
 * <http://www.gnu.org/licenses/>.
 */
package org.commongeoregistry.adapter.metadata;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.commongeoregistry.adapter.RegistryAdapter;
import org.commongeoregistry.adapter.dataaccess.LocalizedValue;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Metadata that describes a hierarchy type, such as Geopolitical or health
 * administrative.
 *
 */
public class HierarchyType implements Serializable
{
  /**
   * 
   */
  private static final long   serialVersionUID           = -1947163248569170534L;

  public static final String  JSON_CODE                  = "code";

  public static final String  JSON_INHERITED_HIER_CODE   = "inheritedHierarchyCode";

  public static final String  JSON_LOCALIZED_LABEL       = "label";

  public static final String  JSON_LOCALIZED_DESCRIPTION = "description";

  public static final String  JSON_ABSTRACT_DESCRIPTION  = "abstractDescription";

  public static final String  JSON_PROGRESS              = "progress";

  public static final String  JSON_ACKNOWLEDGEMENT       = "acknowledgement";

  public static final String  JSON_CONTACT               = "contact";

  public static final String  JSON_ROOT_GEOOBJECTTYPES   = "rootGeoObjectTypes";

  public static final String  JSON_GEOOBJECTTYPE         = "geoObjectType";

  public static final String  JSON_CHILDREN              = "children";

  /**
   * Invariant: Is either an empty string or is the code of a valid
   * {@link OrganizationDTO}.
   */
  public static final String  JSON_ORGANIZARION_CODE     = "organizationCode";

  /**
   * Unique identifier but also human readable.
   */
  private String              code;

  /**
   * The localized label of the hierarchy type for the presentation tier.
   */
  private LocalizedValue      label;

  /**
   * The localized description of the hierarchy type for the presentation tier.
   */
  private LocalizedValue      description;

  /**
   * The organization responsible for this {@link HierarchyType}. This can be
   * null.
   */
  private String              organizationCode;

  private String              abstractDescription;

  private String              progress;

  private String              acknowledgement;

  private String              contact;

  private List<HierarchyNode> rootGeoObjectTypes;

  public HierarchyType(String code, LocalizedValue label, LocalizedValue description, String organizationCode)
  {
    this.code = code;
    this.label = label;
    this.description = description;
    this.organizationCode = organizationCode;
    this.rootGeoObjectTypes = Collections.synchronizedList(new LinkedList<HierarchyNode>());
  }

  public String getCode()
  {
    return this.code;
  }

  public LocalizedValue getLabel()
  {
    return this.label;
  }

  public void setLabel(LocalizedValue label)
  {
    this.label = label;
  }

  public LocalizedValue getDescription()
  {
    return this.description;
  }

  public void setDescription(LocalizedValue description)
  {
    this.description = description;
  }

  /**
   * 
   * @return the code of the {@link OrganizationDTO} (Optional) that manages
   *         this {@link HierarchyType}, or NULL if not managed by an
   *         {@link OrganizationDTO}.
   */
  public String getOrganizationCode()
  {
    return this.organizationCode;
  }

  /**
   * Sets the {@link OrganizationDTO} (Optional) that manages this
   * {@link HierarchyType}.
   * 
   * Precondition: The organization code is valid
   * 
   * @param organizationCode
   *          code of the {@link OrganizationDTO} that manages this
   *          {@link HierarchyType}, or NULL if none.
   */
  public void setOrganizationCode(String organizationCode)
  {
    this.organizationCode = organizationCode;
  }

  public String getAbstractDescription()
  {
    return abstractDescription;
  }

  public void setAbstractDescription(String abstractDescription)
  {
    this.abstractDescription = abstractDescription;
  }

  public String getProgress()
  {
    return progress;
  }

  public void setProgress(String progress)
  {
    this.progress = progress;
  }

  public String getAcknowledgement()
  {
    return acknowledgement;
  }

  public void setAcknowledgement(String acknowledgement)
  {
    this.acknowledgement = acknowledgement;
  }

  public String getContact()
  {
    return contact;
  }

  public void setContact(String contact)
  {
    this.contact = contact;
  }

  public List<HierarchyNode> getRootGeoObjectTypes()
  {
    return this.rootGeoObjectTypes;
  }

  /**
   * Adds root {@link GeoObjectType} objects to the root of the hierarchy type.
   * 
   * @param hierarchyNode
   */
  public void addRootGeoObjects(HierarchyNode hierarchyNode)
  {
    this.rootGeoObjectTypes.add(hierarchyNode);
  }

  /**
   * Represents a node in a {@link HierarchyType} where the node value is a
   * {@link GeoObjectType} and the children are also {@link GeoObjectType}s in
   * the {@link HierarchyType}.
   * 
   * @author nathan
   *
   */
  public static class HierarchyNode
  {
    /**
     * {@link GeoObjectType} in the hierarchies node.
     */
    private GeoObjectType       geoObjectType;

    /**
     * Children {@link GeoObjectType}s in the hierarchy.
     */
    private List<HierarchyNode> children;

    /**
     * If the node is from an inherited hierarchy, this is the code of that
     * hierarchy. If the node is not inherited, this field will be null.
     */
    private String              inheritedHierarchyCode;

    /**
     * 
     * @param _geoObjectType
     *          {@link GeoObjectType} in the hierarchies node.
     */
    public HierarchyNode(GeoObjectType _geoObjectType)
    {
      this(_geoObjectType, null);
    }

    /**
     * 
     * @param _geoObjectType
     *          {@link GeoObjectType} in the hierarchies node.
     */
    public HierarchyNode(GeoObjectType _geoObjectType, String _inheritedHierarchyCode)
    {
      this.geoObjectType = _geoObjectType;
      this.inheritedHierarchyCode = _inheritedHierarchyCode;
      this.children = Collections.synchronizedList(new LinkedList<HierarchyNode>());
    }

    /**
     * Returns the {@link GeoObjectType} defined on this node in the hierarchy.
     * 
     * @return the {@link GeoObjectType} defined on this node in the hierarchy.
     */
    public GeoObjectType getGeoObjectType()
    {
      return this.geoObjectType;
    }

    /**
     * Returns true if the hierarchy (this node or all children) contains the
     * provided GeoObjectType.
     */
    public boolean hierarchyHasGeoObjectType(String typeCode, boolean excludeInherited)
    {
      if ( ( !excludeInherited || ( excludeInherited && this.inheritedHierarchyCode == null ) ) && this.geoObjectType.getCode().equals(typeCode))
      {
        return true;
      }

      for (HierarchyNode node : this.children)
      {
        if (node.hierarchyHasGeoObjectType(typeCode, excludeInherited))
        {
          return true;
        }
      }

      return false;
    }

    /**
     * Add the given child {@link GeoObjectType} to this node in the hierarchy.
     * 
     * @param _child
     *          Child {@link GeoObjectType} to add to the hierarchy.
     */
    public void addChild(HierarchyNode _hierarchyNode)
    {
      this.children.add(_hierarchyNode);
    }

    /**
     * Returns the child nodes of this current node.
     * 
     * @return child nodes of this current node.
     */
    public List<HierarchyNode> getChildren()
    {
      return this.children;
    }

    /**
     * @return The code of the hierarchy which this node is inherited from, if
     *         the node is inherited. If the node is not inherited, then this
     *         will return null.
     */
    public String getInheritedHierarchyCode()
    {
      return this.inheritedHierarchyCode;
    }

    /**
     * Generates JSON for this object.
     * 
     * @return JSON representation of this object.
     */
    public JsonObject toJSON()
    {
      JsonObject jsonObj = new JsonObject();

      jsonObj.addProperty(JSON_GEOOBJECTTYPE, geoObjectType.getCode());
      jsonObj.addProperty(JSON_INHERITED_HIER_CODE, this.getInheritedHierarchyCode());

      JsonArray jaChildren = new JsonArray();
      for (int i = 0; i < children.size(); ++i)
      {
        HierarchyNode hnode = children.get(i);

        jaChildren.add(hnode.toJSON());
      }
      jsonObj.add(JSON_CHILDREN, jaChildren);

      return jsonObj;
    }

    /**
     * Generates JSON for the Hierarchy Node.
     * 
     * @param sJson
     * @param registry
     * @return JSON for the Hierarchy Node.
     */
    protected static HierarchyNode fromJSON(String sJson, RegistryAdapter registry)
    {
      JsonParser parser = new JsonParser();

      JsonObject oJson = parser.parse(sJson).getAsJsonObject();

      GeoObjectType got = registry.getMetadataCache().getGeoObjectType(oJson.get(JSON_GEOOBJECTTYPE).getAsString()).get();

      HierarchyNode node = new HierarchyNode(got);

      JsonArray jaChildren = oJson.getAsJsonArray(JSON_CHILDREN);
      for (int i = 0; i < jaChildren.size(); ++i)
      {
        JsonObject joChild = jaChildren.get(i).getAsJsonObject();

        HierarchyNode hnChild = HierarchyNode.fromJSON(joChild.toString(), registry);

        node.addChild(hnChild);
      }

      return node;
    }
  }

  public JsonObject toJSON()
  {
    return toJSON(new DefaultSerializer());
  }

  public JsonObject toJSON(CustomSerializer serializer)
  {
    JsonObject jsonObj = new JsonObject();

    jsonObj.addProperty(JSON_CODE, this.getCode());

    jsonObj.add(JSON_LOCALIZED_LABEL, this.getLabel().toJSON(serializer));

    jsonObj.add(JSON_LOCALIZED_DESCRIPTION, this.getDescription().toJSON(serializer));

    String organizationString;
    if (this.organizationCode == null)
    {
      organizationString = "";
    }
    else
    {
      organizationString = this.organizationCode;
    }
    jsonObj.addProperty(JSON_ORGANIZARION_CODE, organizationString);

    if (this.abstractDescription != null)
    {
      jsonObj.addProperty(JSON_ABSTRACT_DESCRIPTION, this.abstractDescription);
    }

    if (this.progress != null)
    {
      jsonObj.addProperty(JSON_PROGRESS, this.progress);
    }

    if (this.acknowledgement != null)
    {
      jsonObj.addProperty(JSON_ACKNOWLEDGEMENT, this.acknowledgement);
    }

    if (this.contact != null)
    {
      jsonObj.addProperty(JSON_CONTACT, this.contact);
    }

    jsonObj.addProperty(JSON_ORGANIZARION_CODE, organizationString);

    JsonArray jaRoots = new JsonArray();
    for (int i = 0; i < rootGeoObjectTypes.size(); ++i)
    {
      HierarchyNode hnode = rootGeoObjectTypes.get(i);

      jaRoots.add(hnode.toJSON());
    }

    jsonObj.add(JSON_ROOT_GEOOBJECTTYPES, jaRoots);

    serializer.configure(this, jsonObj);

    return jsonObj;
  }

  /**
   * Constructs a {@link HierarchyType} from the given JSON.
   * 
   * @param _sJson
   * @param _registry
   * @return
   */
  public static HierarchyType fromJSON(String _sJson, RegistryAdapter _registry)
  {
    JsonParser parser = new JsonParser();

    JsonObject oJson = parser.parse(_sJson).getAsJsonObject();

    String code = oJson.get(JSON_CODE).getAsString();
    LocalizedValue label = LocalizedValue.fromJSON(oJson.get(JSON_LOCALIZED_LABEL).getAsJsonObject());
    LocalizedValue description = LocalizedValue.fromJSON(oJson.get(JSON_LOCALIZED_DESCRIPTION).getAsJsonObject());

    String abstractDescription = oJson.has(JSON_ABSTRACT_DESCRIPTION) ? oJson.get(JSON_ABSTRACT_DESCRIPTION).getAsString() : null;
    String progress = oJson.has(JSON_PROGRESS) ? oJson.get(JSON_PROGRESS).getAsString() : null;
    String acknowledgement = oJson.has(JSON_ACKNOWLEDGEMENT) ? oJson.get(JSON_ACKNOWLEDGEMENT).getAsString() : null;
    String contact = oJson.has(JSON_CONTACT) ? oJson.get(JSON_CONTACT).getAsString() : null;

    String organizationCode = null;

    JsonElement jsonOrganization = oJson.get(JSON_ORGANIZARION_CODE);
    if (jsonOrganization != null)
    {
      organizationCode = jsonOrganization.getAsString();
    }

    HierarchyType ht = new HierarchyType(code, label, description, organizationCode);
    ht.setAbstractDescription(abstractDescription);
    ht.setProgress(progress);
    ht.setAcknowledgement(acknowledgement);
    ht.setContact(contact);

    JsonArray rootGeoObjectTypes = oJson.getAsJsonArray(JSON_ROOT_GEOOBJECTTYPES);
    if (rootGeoObjectTypes != null)
    {
      for (int i = 0; i < rootGeoObjectTypes.size(); ++i)
      {
        HierarchyNode node = HierarchyNode.fromJSON(rootGeoObjectTypes.get(i).getAsJsonObject().toString(), _registry);

        ht.addRootGeoObjects(node);
      }
    }

    return ht;
  }

  public static HierarchyType[] fromJSONArray(String saJson, RegistryAdapter adapter)
  {
    JsonParser parser = new JsonParser();

    JsonArray jaHts = parser.parse(saJson).getAsJsonArray();
    HierarchyType[] hts = new HierarchyType[jaHts.size()];
    for (int i = 0; i < jaHts.size(); ++i)
    {
      HierarchyType ht = HierarchyType.fromJSON(jaHts.get(i).toString(), adapter);
      hts[i] = ht;
    }

    return hts;
  }
}
