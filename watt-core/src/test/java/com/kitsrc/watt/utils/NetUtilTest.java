package com.kitsrc.watt.utils;

import java.net.InetSocketAddress;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NetUtilTest {

    @Test
    void isIPV4() {
    }

    @Test
    void isIPV6() {
    }

    @Test
    void toStringAddress() {
    }

    @Test
    void toIpAddress() {
    }

    @Test
    void testToStringAddress() {
    }

    @Test
    void toInetSocketAddress() {
    }

    @Test
    void toLong() {
    }

    @Test
    void getLocalIp() {
    }

    @Test
    void getLocalHost() {
    }

    @Test
    void getLocalAddress() {
    }

    @Test
    void validAddress() {
    }

    @Test
    void isValidIp() {
    }

    @Test
    void testIsValidIp() {
    }

    @Test
    void format() {
    }
    private Integer port = 1234;
    private String v4addr = "127.0.0.1";
    private String v6addr = "[0:0:0:0:0:0:0:1]";
    private String v6addr2 = "[2600:0:0:0:0:0:0:0]";
    private String v4local = v4addr + ":" + port.toString();
    private String v6local = v6addr + ":" + port.toString();
    private String v6ext = v6addr2 + ":" + port.toString();

    @Test
    public void testFormatInetAddrGoodIpv4() {
        InetSocketAddress isa = new InetSocketAddress(v4addr, port);
        assertEquals("127.0.0.1:1234", NetUtil.format(isa));
    }

    @Test
    public void testFormatInetAddrGoodIpv6Local() {
        // Have to use the expanded address here, hence not using v6addr in instantiation
        InetSocketAddress isa = new InetSocketAddress("::1", port);
        assertEquals(v6local, NetUtil.format(isa));
    }

    @Test
    public void testFormatInetAddrGoodIpv6Ext() {
        // Have to use the expanded address here, hence not using v6addr in instantiation
        InetSocketAddress isa = new InetSocketAddress("2600::", port);
        assertEquals(v6ext, NetUtil.format(isa));
    }

    @Test
    public void testFormatInetAddrGoodHostname() {
        InetSocketAddress isa = new InetSocketAddress("localhost", 1234);

        assertThat(NetUtil.format(isa), anyOf(equalTo(v4local), equalTo(v6local)));
    }

    @Test
    public void testFormatAddrUnresolved() {
        InetSocketAddress isa = InetSocketAddress.createUnresolved("doesnt.exist.com", 1234);
        assertEquals("doesnt.exist.com:1234", NetUtil.format(isa));
    }
}