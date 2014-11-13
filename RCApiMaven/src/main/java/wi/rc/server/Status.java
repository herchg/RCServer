package wi.rc.server;

import wi.core.pattern.INumericValuable;

/**
 *
 * @author hermeschang
 */
public enum Status implements INumericValuable {

    Invalid(0), Valid(1), Deleted(2);

    private final int mValue;

    private Status(int value) {
        this.mValue = value;
    }

    @Override
    public Integer getValue() {
        return this.mValue;
    }

}
