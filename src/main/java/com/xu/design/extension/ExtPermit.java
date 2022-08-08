package com.xu.design.extension;

import com.xu.design.utils.CommonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExtPermit extends MessageDTO {

    /**
     * success -> false 扩展点流程将随之中断
     *
     * @param success
     * @param code
     * @param msg
     */
    public ExtPermit(boolean success, String code, String msg) {
        if (!success) {
            ExtContext.markBreak();
        }
        setSuccess(success);
        setCode(code);
        setMsg(msg);
    }

    public ExtPermit orElse(String msg) {
        if (!isSuccess()) {
            setMsg(msg);
        }
        return this;
    }

    public static ExtPermit of(Boolean success) {
        if (CommonUtils.toBool(success)) {
            return newSuccess();
        } else {
            return newFailure();
        }
    }

    public static ExtPermit newSuccess() {
        return new ExtPermit(true, null, null);
    }

    public static ExtPermit newFailure() {
        return newFailure(null, null);
    }

    public static ExtPermit newFailure(String msg) {
        return newFailure(null, msg);
    }

    public static ExtPermit newFailure(String code, String msg) {
        return new ExtPermit(false, null, msg);
    }
}