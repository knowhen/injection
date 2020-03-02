package when.injection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: when
 * @create: 2020-03-02  11:44
 **/
public class BeanDefinition {
    private String id;
    private String className;
    private List<ConstructorArg> constructorArgs = new ArrayList<>();
    private Scope scope = Scope.SINGLETON;
    private boolean lazyInit = false;

    public BeanDefinition() {
    }

    public BeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    public List<ConstructorArg> getConstructorArgs() {
        return constructorArgs;
    }

    public boolean isLazyInit() {
        return lazyInit;
    }

    public boolean isSingleton() {
        return scope.equals(Scope.SINGLETON);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setConstructorArgs(List<ConstructorArg> constructorArgs) {
        this.constructorArgs = constructorArgs;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    public void addConstructorArg(ConstructorArg constructorArg) {
        constructorArgs.add(constructorArg);
    }

    public static class ConstructorArg {
        private boolean ref;
        private Class type;
        private Object arg;

        public ConstructorArg(boolean ref, Class type, Object arg) {
            this.ref = ref;
            this.type = type;
            this.arg = arg;
        }

        public boolean isRef() {
            return ref;
        }

        public void setRef(boolean ref) {
            this.ref = ref;
        }

        public Class getType() {
            return type;
        }

        public void setType(Class type) {
            this.type = type;
        }

        public Object getArg() {
            return arg;
        }

        public void setArg(Object arg) {
            this.arg = arg;
        }
    }
}
