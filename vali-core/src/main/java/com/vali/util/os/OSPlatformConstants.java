package com.vali.util.os;

/**
 * Created with IntelliJ IDEA
 *
 * @author : LiJie
 * @date : 2019/2/25
 * @time : 17:22
 * Description:
 */
public enum OSPlatformConstants {
    /**
     * any
     */
    Any("any"),
    Linux("Linux"),
    Mac_OS("Mac OS"),
    Mac_OS_X("Mac OS X"),
    Windows("Windows"),
    OS2("OS/2"),
    Solaris("Solaris"),
    SunOS("SunOS"),
    MPEiX("MPE/iX"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FreeBSD("FreeBSD"),
    Irix("Irix"),
    Digital_Unix("Digital Unix"),
    NetWare_411("NetWare"),
    OSF1("OSF1"),
    OpenVMS("OpenVMS"),
    Others("Others"),
    ;

    private String desc;

    private OSPlatformConstants(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }

    String desc() {
        return this.desc;
    }
}
