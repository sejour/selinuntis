package github.sejour.selinuntis.core;

public abstract class AbstractObjectInfo implements ObjectInfo {
    @Override
    public boolean equals(Object other) {
        if (other instanceof ObjectInfo) {
            return getAlias().equals(((ObjectInfo) other).getAlias());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getAlias().hashCode();
    }
}
