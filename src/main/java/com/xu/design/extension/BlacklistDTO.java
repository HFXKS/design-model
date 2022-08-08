package com.xu.design.extension;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
@Accessors(chain = true)
public class BlacklistDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 白名单
     */
    private List<String> whitelist;

    /**
     * 黑名单
     */
    private List<String> blacklist;

    /**
     * 是否命中白名单
     *
     * @param code
     * @return
     */
    public boolean isMatchWhite(String code) {
        return Optional.ofNullable(this.whitelist).map(x -> x.contains(code)).orElse(false);
    }

    /**
     * 是否命中黑名单
     *
     * @param code
     * @return
     */
    public boolean isMatchBlack(String code) {
        return Optional.ofNullable(this.blacklist).map(x -> x.contains(code)).orElse(false);
    }
}