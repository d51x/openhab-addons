/**
 * Copyright (c) 2010-2024 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.bluetooth.bluegiga.internal.enumeration;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/**
 * Class to implement the BlueGiga Enumeration <b>ConnectionStatusFlag</b>.
 * <p>
 * The possible connection status flags are described in the table below. The flags field is a
 * bit mask, so multiple flags can be set at a time. If the bit is 1 the flag is active and if the bit is
 * 0 the flag is inactive.
 * <p>
 * Note that this code is autogenerated. Manual changes may be overwritten.
 *
 * @author Chris Jackson - Initial contribution of Java code generator
 */
@NonNullByDefault
public enum ConnectionStatusFlag {
    /**
     * Default unknown value
     */
    UNKNOWN(-1),

    /**
     * [1] This status flag tells the connection exists to a remote device.
     */
    CONNECTION_CONNECTED(0x0001),

    /**
     * [2] This flag tells the connection is encrypted.
     */
    CONNECTION_ENCRYPTED(0x0002),

    /**
     * [4] Connection completed flag, which is used to tell a new connection has been created.
     */
    CONNECTION_COMPLETED(0x0004),

    /**
     * [8] This flag tells that connection parameters have changed and. It is set when connection
     * parameters have changed due to a link layer operation.
     */
    CONNECTION_PARAMETERS_CHANGE(0x0008);

    /**
     * A mapping between the integer code and its corresponding type to
     * facilitate lookup by code.
     */
    private static @Nullable Map<Integer, ConnectionStatusFlag> codeMapping;

    private int key;

    private ConnectionStatusFlag(int key) {
        this.key = key;
    }

    /**
     * Lookup function based on the type code. Returns {@link UNKNOWN} if the code does not exist.
     *
     * @param connectionStatusFlag
     *            the code to lookup
     * @return enumeration value.
     */
    public static ConnectionStatusFlag getConnectionStatusFlag(int connectionStatusFlag) {
        Map<Integer, ConnectionStatusFlag> localCodeMapping = codeMapping;
        if (localCodeMapping == null) {
            localCodeMapping = new HashMap<>();
            for (ConnectionStatusFlag s : values()) {
                localCodeMapping.put(s.key, s);
            }
            codeMapping = localCodeMapping;
        }

        return localCodeMapping.getOrDefault(connectionStatusFlag, UNKNOWN);
    }

    /**
     * Returns the BlueGiga protocol defined value for this enum
     *
     * @return the BGAPI enumeration key
     */
    public int getKey() {
        return key;
    }
}
