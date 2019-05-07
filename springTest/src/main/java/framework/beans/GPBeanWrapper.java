package framework.beans;

public class GPBeanWrapper {

    private Object wrappedInstance;

    private Class<?> wrappedClass;

    public GPBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance() {
        return wrappedInstance;
    }

    public Class<?> getWrappedClass() {
        return wrappedInstance.getClass();
    }
}
