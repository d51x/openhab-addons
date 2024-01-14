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
package org.openhab.binding.digitalstrom.internal.lib.manager;

import java.util.List;

import org.openhab.binding.digitalstrom.internal.lib.event.EventHandler;
import org.openhab.binding.digitalstrom.internal.lib.listener.ManagerStatusListener;
import org.openhab.binding.digitalstrom.internal.lib.listener.SceneStatusListener;
import org.openhab.binding.digitalstrom.internal.lib.listener.stateenums.ManagerStates;
import org.openhab.binding.digitalstrom.internal.lib.listener.stateenums.ManagerTypes;
import org.openhab.binding.digitalstrom.internal.lib.structure.devices.Device;
import org.openhab.binding.digitalstrom.internal.lib.structure.scene.InternalScene;

/**
 * The {@link SceneManager} manages all functions concerning scenes without sending the commands itself.
 *
 * <p>
 * So it manages a list of all {@link InternalScene} they called in the past or was generated by calling
 * {@link #generateScenes()}.<br>
 * Through this class you can also register {@link SceneStatusListener}'s to the {@link InternalScene}'s or register a
 * scene discovery. With {@link #addEcho(String)} or {@link #addEcho(String, short)} scene calls form the library can be
 * ignored. To update the state of an {@link InternalScene} or {@link Device} the methods
 * {@link #callInternalScene(InternalScene)}, {@link #callInternalScene(String)},
 * {@link #callDeviceScene(Device, Short)}
 * , {@link #callDeviceScene(String, Short)} etc. can be used.
 *
 * <p>
 * If you call the {@link #start()} method an {@link org.openhab.binding.digitalstrom.internal.lib.event.EventListener}
 * will be started to handle scene calls and undos from the outside.
 *
 * @author Michael Ochel - Initial contribution
 * @author Matthias Siegele - Initial contribution
 *
 */
public interface SceneManager extends EventHandler {

    /**
     * Activates the given {@link InternalScene}, if it exists. Otherwise it will be added to the scene list and
     * activated, if it is a callable scene.
     *
     * @param scene to call
     */
    void callInternalScene(InternalScene scene);

    /**
     * Activates an {@link InternalScene} with the given id, if it exists. Otherwise a new
     * {@link InternalScene} will be created and activated, if it is a callable scene.
     *
     * @param sceneID of the scene to call
     */
    void callInternalScene(String sceneID);

    /**
     * Call the given sceneID on the {@link Device} with the given dSID, if the {@link Device} exists.
     *
     * @param dSID of the {@link Device} to call
     * @param sceneID of the scene to call
     */
    void callDeviceScene(String dSID, Short sceneID);

    /**
     * Call the given sceneID on the given {@link Device}, if the {@link Device} exists.
     *
     * @param device to call
     * @param sceneID to call
     */
    void callDeviceScene(Device device, Short sceneID);

    /**
     * Deactivates the given {@link InternalScene}, if it exists. Otherwise it will added to the scene list and
     * deactivated, if it is a callable scene.
     *
     * @param scene to undo
     */
    void undoInternalScene(InternalScene scene);

    /**
     * Deactivates an {@link InternalScene} with the given sceneID, if it exists. Otherwise a new
     * {@link InternalScene} will be created and deactivated, if it is a callable scene.
     *
     * @param sceneID of the scene to undo
     */
    void undoInternalScene(String sceneID);

    /**
     * Undo the last scene on the {@link Device} with the given dSID, if the {@link Device} exists.
     *
     * @param dSID of the {@link Device} to undo
     */
    void undoDeviceScene(String dSID);

    /**
     * Undo the last scene on the {@link Device}, if the {@link Device} exists.
     *
     * @param device the {@link Device} to undo
     */
    void undoDeviceScene(Device device);

    /**
     * Registers the given {@link SceneStatusListener} to the {@link InternalScene}, if it exists or registers it as a
     * Scene-Discovery if the id of the {@link SceneStatusListener} is {@link SceneStatusListener#SCENE_DISCOVERY}.
     *
     * @param sceneListener to register
     */
    void registerSceneListener(SceneStatusListener sceneListener);

    /**
     * Unregisters the given {@link SceneStatusListener} from the {@link InternalScene}, if it exists or unregisters the
     * Scene-Discovery, if the id of the {@link SceneStatusListener} is {@link SceneStatusListener#SCENE_DISCOVERY}.
     *
     * @param sceneListener to register
     */
    void unregisterSceneListener(SceneStatusListener sceneListener);

    /**
     * Adds the given {@link InternalScene} to the scene list, if it is a callable scene.
     *
     * @param intScene to add
     */
    void addInternalScene(InternalScene intScene);

    /**
     * Adds the scene call with the given dSID and sceneId as an echo to ignore them by detecting the
     * {@link org.openhab.binding.digitalstrom.internal.lib.event.types.EventItem}.
     *
     * @param dSID of the {@link Device} that will be ignored
     * @param sceneId of the scene that will be ignored
     */
    void addEcho(String dSID, short sceneId);

    /**
     * Adds the scene call with the given internal scene id as an echo to ignore them by detecting the
     * {@link org.openhab.binding.digitalstrom.internal.lib.event.types.EventItem}
     * .
     *
     * @param internalSceneID to ignore
     */
    void addEcho(String internalSceneID);

    /**
     * Returns the list of all {@link InternalScene}.
     *
     * @return list of all scenes
     */
    List<InternalScene> getScenes();

    /**
     * Returns true, if all reachable scenes are already generated, otherwise false.
     *
     * @return true = reachable scenes generated, otherwise false
     */
    boolean scenesGenerated();

    /**
     * Generates all reachable scenes.
     *
     */
    void generateScenes();

    /**
     * Will be called from the {@link org.openhab.binding.digitalstrom.internal.lib.structure.scene.SceneDiscovery},
     * if a scene type is generated or is fail.<br>
     * For that the scenesGenerated char array has four chars. Each char represents one scene type in the following
     * direction:
     * <ul>
     * <li><b>first:</b> named scenes</li>
     * <li><b>second:</b> apartment scenes</li>
     * <li><b>third:</b> zone scenes</li>
     * <li><b>fourth</b>: group scenes, if they can call by push buttons</li>
     * </ul>
     * If a scene type is not generated the char is "0". If a scene type is generated the char is "1" and, if it is fail
     * the char is "2".
     *
     * @param scenesGenerated array
     */
    void scenesGenerated(char[] scenesGenerated);

    /**
     * Returns true, if a discovery is registered, otherwise false.
     *
     * @return true discovery is registered, otherwise false
     */
    boolean isDiscoveryRegistrated();

    /**
     * Starts the {@link org.openhab.binding.digitalstrom.internal.lib.event.EventListener}.
     */
    void start();

    /**
     * Stops the {@link org.openhab.binding.digitalstrom.internal.lib.event.EventListener}.
     */
    void stop();

    /**
     * Removes the {@link InternalScene} with the given sceneID.
     *
     * @param sceneID of the {@link InternalScene} to remove
     */
    void removeInternalScene(String sceneID);

    /**
     * Returns the {@link InternalScene} with the given sceneID.
     *
     * @param sceneID of the {@link InternalScene}
     * @return internal scenes
     */
    InternalScene getInternalScene(String sceneID);

    /**
     * Registers the given {@link ManagerStatusListener} to this class.
     *
     * @param statusListener to register
     */
    void registerStatusListener(ManagerStatusListener statusListener);

    /**
     * Unregisters the {@link ManagerStatusListener} from this class.
     */
    void unregisterStatusListener();

    /**
     * Returns the {@link ManagerTypes} of this class.
     *
     * @return these {@link ManagerTypes}
     */
    ManagerTypes getManagerType();

    /**
     * Returns the current {@link ManagerStates}.
     *
     * @return current {@link ManagerStates}
     */
    ManagerStates getManagerState();

    /**
     * Calls a scene without inform the scene discovery about the conceivably new {@link InternalScene}.
     *
     * @param zoneID to call
     * @param groupID to call
     * @param sceneID to call
     */
    void callInternalSceneWithoutDiscovery(Integer zoneID, Short groupID, Short sceneID);
}
