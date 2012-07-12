package Binary3.Binary3;

import java.util.Map;

import org.snaplogic.cc.ComponentAPI;
import org.snaplogic.cc.InputView;
import org.snaplogic.cc.OutputView;
import org.snaplogic.cc.ErrorView;
import org.snaplogic.common.ComponentResourceErr;

/**
 * <p>
 * Component developers creating new components must derive from
 * ComponentAPI class. In this class, the execute() method must be overridden
 * to implement the data processing logic of the component. Apart from this key
 * method, the developer can also override the following methods to make their
 * component easier to use.
 * <ul>
 * <li>{@link #createResourceTemplate()}: This method can be implemented to provide clients
 * that are creating resource definitions with a template definition which defines
 * properties, views, parameters and other values that must always be present in the
 * resource definition for that component.
 * 
 * <li>{@link #suggestResourceValues(ComponentResourceErr)}: This method can be implemented to provide clients
 * that are creating resource definitions with a helper function which fills in
 * suggested values for the resource definition, based on the resource values already
 * entered by the client.
 * 
 * <li>{@link #validate(ComponentResourceErr)}: This method can be implemented to allow the component to validate
 * properties, views and other values that are very specific to that component.
 * 
 * <li>{@link #validateConfigFile()}: This method can be implemented to validate the contents of the
 * configuration file (if any) for the component.
 * </ul>
 */
public class ReadComponent extends ComponentAPI {
    
    /**
     * Returns the component API version.
     * <p>
     * The component API is versioned. The method getAPIVersion needs to
     * be overridden in component classes, because it is checked when
     * components are registered with the component container to ensure 
     * that the current API of the component container is compatible with 
     * the version of the API to which this component was written.
     * 
     * @return Component API version as string, e.g. "1.0"
     */
    public String getAPIVersion() {
        return "1.0";
    }
    
    /**
     * Returns the component version.
     * <p>
     * Components must implement getComponentVersion for resource upgrades to work properly.
     * Initially component version should be set to "1.0".
     * Then as resource definition needs to change component version changes to 1.1, 1.2, etc,
     * and upgrade methods such as upgrade_1_0_to_1_1 are added to the component implementation.
     * 
     * @return Component version string such as "1.0"
     */
    public String getComponentVersion() {
        return "1.0";
    }
    
    /**
     * Returns the component documentation URI.
     * <p>  
     * This is the URI at which detailed documentation about the component can be found.
     * Should be overridden.
     *  
     * @return Component documentation URI
     */
    @Override
    public String getDocURI() {
        // Do nothing by default.
        return super.getDocURI();
    }    
    
    /**
     * Returns the component description.
     * <p>
     * This is the description that will be shown by the designer.
     * 
     * @return component description
     */
    @Override
    public String getDescription() {
        return "This component is a sample read component.";
    }

    /**
     * Returns the component label.
     * <p>
     * This is the label that will be shown by the designer.
     * 
     * @return component label
     */    
    @Override
    public String getLabel() {
        return "Sample Read Component";
    }         
    
    /**
     * Creates a resource template for the component.
     * <p> 
     * Most Components have resource definitions that have a certain pattern or template. For example:
     * <ul>
     * <li>Component specific properties like "filename"
     * <li>Default view names and their structures.
     * <li>Default property values, etc.
     * </ul>
     * In order to provide the client who is designing a resource based on the component with such a
     * template this method has been provided. The component author should use this method to define
     * component specific properties and fill in expected values for properties and views.
     */
    @Override
    public void createResourceTemplate() {
        // Do nothing by default
        super.createResourceTemplate();
    }
    
    /**
     * Suggests the values of the resource definition.
     * <p>
     * This is an optional method that can be overridden to provide the component with the capability
     * to suggest values for the resource definition, based on the values already provided by the
     * resource author. If the component is unable to make suggestions, due to errors in the resource
     * definition, then this method can use the resdefError object to specify what those errors are.
     * 
     * @param resdefError This object provides a convenient interface for setting error messages
     *                    on invalid values in the resource definition.
     */
    @Override
    public void suggestResourceValues(ComponentResourceErr resdefError) {
        // Do nothing by default
        super.suggestResourceValues(resdefError);
    }
    
    /**
     * Create error view(s) for the component.  
     * 
     */
    @Override
    public void suggestErrorViews() {
        // Do nothing by default
        super.suggestErrorViews();
    }    
    
    /**
     * Validate the resource definition for the component.
     * 
     * This is an optional method, that can be overridden to provide the component with the capability
     * to validate the contents of the resource definition. The errors found should be set using the
     * {@link ComponentResourceErr#setMessage(String, Object...)} methods provided by the resdef_error object.
     * 
     * @param resdefError This object provides a convenient interface for setting error messages
     *                      on invalid values in the resource definition.
     */
    @Override
    public void validate(ComponentResourceErr resdefError) {
        // Do nothing by default
        super.validate(resdefError);
    }
    
    /**
     * Validates the component config file.
     * <p>
     * The Component Container (CC) will parse the contents of the config file into a dictionary at startup
     * or reconfiguration time and then call this method to validate the contents of the dictionary. The
     * dictionary is made available via {@link ComponentAPI#getConfig()} method of this class.
     */
    @Override
    public void validateConfigFile() {
        // Do nothing by default
        super.validateConfigFile();
    }    

    /**
     * Component authors may override this to provide a way to stop a
     * component that is, for example, blocked on waiting for some 
     * resource (e.g., call {@link Socket#shutdownInput()}. This will be called 
     * from {@link ComponentThread#stopRunning()}. 
     */
    @Override
    public void stop() {
         super.stop();
    }
    
    /**
     * Executes the component code.
     * <p>
     * There are two overloaded variations of the execute() method, one that takes in error views,
     * and one that doesn't.  If component supports error views by declaring the ERROR_VIEWS capability
     * in the getCapabilities() method, it must implement the execute() method taking error views,
     * otherwise it must implement the execute() method taking only input and output views.  
     * 
     * Component must override one of the two execute methods.
     * 
     * @param inputViews    Input views
     * @param outputViews   Output views
     */
    @Override
    public void execute(Map<String, InputView> inputViews, Map<String, OutputView> outputViews) {
        // Implement this method.  If your component supports error views, delete this method
        // and instead implement the other execute method that takes error views.
    } 
    
//    /**
//     * Executes the component code.
//     * <p>
//     * There are two overloaded variations of the execute() method, one that takes in error views,
//     * and one that doesn't.  If component supports error views by declaring the ERROR_VIEWS capability
//     * in the getCapabilities() method, it must implement the execute() method taking error views,
//     * otherwise it must implement the execute() method taking only input and output views.  
//     * 
//     * Component must override one of the two execute methods.
//     * 
//     * @param inputViews    Input views
//     * @param outputViews   Output views
//     * @param errorViews    Error views
//     */
//    @Override
//    public void execute(Map<String, InputView> inputViews, Map<String, OutputView> outputViews, Map<String, ErrorView> errorViews) {
//        
//    }
    
}