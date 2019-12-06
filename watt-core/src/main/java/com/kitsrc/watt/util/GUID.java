package com.kitsrc.watt.util;

import java.net.InetAddress;
import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;


public class GUID {
    private static String sid;
    private static MessageDigest md5 = null;
    private static Random random = new Random();

    public GUID() {
        init();
    }

    public void init() {
        try {
            sid = InetAddress.getLocalHost()
                             .toString();
        } catch (Exception e) {
            sid = "";
        }
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            md5 = null;
        }
    }

    /**
     * @see GUID#uuid()
     * @return
     */
    @Deprecated
    public String newGuid() {
        return uuid();
        //long time = System.currentTimeMillis();
        //StringBuffer seed = new StringBuffer();
        //seed.append(sid);
        //seed.append(":");
        //seed.append(time);
        //seed.append(":");
        //seed.append(random.nextLong());
        //StringBuffer sb = new StringBuffer();
        //if (md5 != null) {
        //    md5.update(seed.toString()
        //                   .getBytes());
        //    byte[] result = md5.digest();
        //    md5.reset();
        //    for (int j = 0; j < result.length; ++j) {
        //        int b = result[j] & 0xFF;
        //        if (b < 0x10) {
        //            sb.append('0');
        //        }
        //        sb.append(Integer.toHexString(b));
        //    }
        //} else {
        //    sb.append(Long.toHexString(time));
        //    sb.append(Long.toHexString(random.nextLong()));
        //}
        //return sb.toString();
    }

    /**
     * that will perform reasonably when used by multiple threads under load.
     *
     * @return
     */
    public String uuid() {
        final Random rnd = ThreadLocalRandom.current();
        long mostSig = rnd.nextLong();
        long leastSig = rnd.nextLong();

        // Identify this as a version 4 UUID, that is one based on a random value.
        mostSig &= 0xffffffffffff0fffL;
        mostSig |= 0x0000000000004000L;

        // Set the variant identifier as specified for version 4 UUID values.  The two
        // high order bits of the lower word are required to be one and zero, respectively.
        leastSig &= 0x3fffffffffffffffL;
        leastSig |= 0x8000000000000000L;

        return new UUID(mostSig, leastSig).toString()
                                          .replaceAll("-", "");
    }

}
