package when.injection;

/**
 * @author: when
 * @create: 2020-03-03  21:05
 * 1.ref为true，arg表示String类型的ref bean id，type不需要设置
 * 2.ref为false，arg、type不能为空
 **/
public class ConstructorArg {
    private boolean referenceType;
    private Class type;
    private Object arg;

    private ConstructorArg(Builder builder) {
        this.referenceType = builder.referenceType;
        this.type = builder.type;
        this.arg = builder.arg;
    }

    public boolean isReferenceType() {
        return referenceType;
    }

    public Class getType() {
        return type;
    }

    public Object getArg() {
        return arg;
    }

    public static class Builder {
        private boolean referenceType;
        private Class type;
        private Object arg;

        public Builder(boolean referenceType) {
            this.referenceType = referenceType;
        }

        public ConstructorArg build() {
            if (referenceType) {
                shouldBeString(arg);
            } else {
                shouldNotBeNull(arg);
                shouldNotBeNull(type);
            }
            return new ConstructorArg(this);
        }

        public Builder setType(Class type) {
            this.type = type;
            return this;
        }

        public Builder setArg(Object arg) {
            this.arg = arg;
            return this;
        }

        private void shouldBeString(Object arg) {
            if (!(arg instanceof String)) {
                throw new IllegalArgumentException("Reference type needs arg of String.");
            }
        }

        private void shouldBeNull(Object o) {
            if (o == null) {
                throw new IllegalArgumentException("Reference type don't need specific type.");
            }
        }

        private void shouldNotBeNull(Object o) {
            if (o == null) {
                throw new IllegalArgumentException("Primitive type needs specific type and arg.");
            }
        }
    }
}
