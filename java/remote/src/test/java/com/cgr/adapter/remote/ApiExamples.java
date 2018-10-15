package com.cgr.adapter.remote;

import java.util.List;
import java.util.Optional;

import org.commongeoregistry.adapter.Term;
import org.commongeoregistry.adapter.android.RegistryAdapterAndroid;
import org.commongeoregistry.adapter.dataaccess.ChildTreeNode;
import org.commongeoregistry.adapter.dataaccess.GeoObject;
import org.commongeoregistry.adapter.metadata.AttributeTermType;
import org.commongeoregistry.adapter.metadata.AttributeType;
import org.commongeoregistry.adapter.metadata.GeoObjectType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class ApiExamples
{

  public void tutorialExamples()
  {
    // Instantiate a mobile adapter reference,
    
    // URL to the 
    String commonGeoRegistryURL = "";
    
    RegistryAdapterAndroid registryAdapter = new RegistryAdapterAndroid(commonGeoRegistryURL);
    
    
    // Populate the registry instance with meadata from the registry
    registryAdapter.refreshMetadataCache();
    
    // Create a new and empty instance of a {@link GeoObject} to populate with values by the mobile host.
    // Pass in the code of the {@link GeoObjectType}/
    GeoObject geoObject = registryAdapter.newGeoObjectInstance("HEALTH_FACILITY");
    
    // Set a value on an attribute
    geoObject.setValue("numberOfBeds", 100);
        
    // Set the geometry (using JTS library)
    Coordinate newCoord = new Coordinate(0,0);
    Point point = new GeometryFactory().createPoint(newCoord);
    geoObject.setGeometry(point);
    
    registryAdapter.getLocalCache().cache(geoObject);
    
    
    // Get a hierarchy of a {@link GeoObject} and their relationships with other {@link GeoObject}s. This will be used
    // to cache a set of {@link GeoObject}s for offline use.
    // We need to come up with the way that the mobile host application will get the identifier of the location that it wishes
    // to cache offline.
    ChildTreeNode childTreeNode = registryAdapter.getChildGeoObjects("UID-123456", new String[] {"VILLAGE", "HOUSEHOLD", "HEALTH_FACILITY"}, true);
    
    // Persist the tree of objects into the local cache.
    registryAdapter.getLocalCache().cache(childTreeNode);
    
    // Fetch a tree of objects from the local cache, such as the households in a village. These will
    // Need to have been fetched from the CGR and cached when online (see example above).
    ChildTreeNode houseHoldsInVillages = registryAdapter.getLocalCache().getChildGeoObjects("A Village UID", new String[] {"HOUSEHOLD"}, true);
    
    // To render a form in the Geospatial widget or the host application, you will need to get the metadata
    // of the object type in order to know what attributes to render and what their localized values are.
    // Since we have not defined how we are doing error handling 
    Optional<GeoObjectType> optionGeoObjectType = registryAdapter.getMetadataCache().getGeoObjectType("HEATH_FACILITY");
    
    if (optionGeoObjectType.isPresent())
    {
      GeoObjectType geoObjectType = optionGeoObjectType.get();
      
      // Get the localized label of the type
      String typeLocalizedLabel = geoObjectType.getLocalizedLabel();
      
      // Get the localized description of the type. 
      String typeLocalizedDescription = geoObjectType.getLocalizedDescription();
      
      for (AttributeType attributeType : geoObjectType.getAttributeMap().values())
      {
        // Get the localized Label
        String attributeLocalizedLabel = attributeType.getLocalizedLabel();
        
        // Get the localized attribute description
        String attributeLocalizedDescription = attributeType.getLocalizedDescription();
        
        // For term (select list) attributes
        
        if (attributeType instanceof AttributeTermType)
        {
          AttributeTermType attributeTermType = (AttributeTermType)attributeType;
          
          // Assuming this is a single dimension list instead of a tree (which the abstraction supports but
          // we will assume single dimension for now.
          List<Term> terms = attributeTermType.getTerms();
          
          for (Term term: terms)
          {
            // Label of the option, such as "Maternity Ward"
            String termLabel = term.getLocalizedLabel();
            
            // Description of the option, such as "Maternity Wards focus on..."
            String termDescription = term.getLocalizedDescription();
            
            // This is the computer readable value, such as "MATURNITY_WARD"
            String value = term.getCode();
          }
        }
        
      }
    }
    
    // Define exception methodology and exception types
    // Do we want an "isRequired" property on {@link AttributeType}
    
    
  }
  
  
}
