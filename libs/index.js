
import { NativeModules, Platform } from 'react-native';
const isAndroid = Platform.OS == 'android';
const { ReactNativeVideocast } = NativeModules;

const SAFE_TIMEOUT = 3600;

function isInvalide(obj) {
    return obj == undefined || obj == null;
}

const scan = (timeout, onDevicesChanged, onError) => {
    if (!isAndroid) return;
    let _timeout = 0;
    if (timeout != undefined) {
        _timeout = (timeout > SAFE_TIMEOUT) ? (SAFE_TIMEOUT) : (timeout <= 0 ? SAFE_TIMEOUT : timeout);
    }
    let _onDevicesChanged = (devices) => { };
    let _onError = (e) => { };

    ReactNativeVideocast.scan(
        _timeout,
        isInvalide(onDevicesChanged) ? _onDevicesChanged : onDevicesChanged,
        isInvalide(onError) ? _onError : onError
    );
}
const setCurrentURI = async (uuid, uri, onSuccess, onError) => {
    if (!isAndroid) return;
    // String uuid,String uri,Callback onSuccess,Callback onError
    let _uuid = isInvalide(uuid) ? "" : uuid;
    let _uri = isInvalide(uri) ? "" : uri;
    let _onSuccess = (v) => { };
    let _onError = (e) => { };
    ReactNativeVideocast.setCurrentURI(
        _uuid,
        _uri,
        isInvalide(onSuccess) ? _onSuccess : onSuccess,
        isInvalide(onError) ? _onError : onError
    );
}
const play = (uuid, onSuccess, onError) => {
    if (!isAndroid) return;
    let _uuid = isInvalide(uuid) ? "" : uuid;
    let _onSuccess = (v) => { };
    let _onError = (e) => { };
    ReactNativeVideocast.play(
        _uuid,
        isInvalide(onSuccess) ? _onSuccess : onSuccess,
        isInvalide(onError) ? _onError : onError
    )
}
const pause = (uuid, onSuccess, onError) => {
    if (!isAndroid) return;
    let _uuid = isInvalide(uuid) ? "" : uuid;
    let _onSuccess = (v) => { };
    let _onError = (e) => { };
    ReactNativeVideocast.pause(
        _uuid,
        isInvalide(onSuccess) ? _onSuccess : onSuccess,
        isInvalide(onError) ? _onError : onError
    )
}
const previous = (uuid, onSuccess, onError) => {
    if (!isAndroid) return;
    let _uuid = isInvalide(uuid) ? "" : uuid;
    let _onSuccess = (v) => { };
    let _onError = (e) => { };
    ReactNativeVideocast.previous(
        _uuid,
        isInvalide(onSuccess) ? _onSuccess : onSuccess,
        isInvalide(onError) ? _onError : onError
    )
}
const next = (uuid, onSuccess, onError) => {
    if (!isAndroid) return;
    let _uuid = isInvalide(uuid) ? "" : uuid;
    let _onSuccess = (v) => { };
    let _onError = (e) => { };
    ReactNativeVideocast.next(
        _uuid,
        isInvalide(onSuccess) ? _onSuccess : onSuccess,
        isInvalide(onError) ? _onError : onError
    )
}
const stopScan = () => {
    if (!isAndroid) return;
    ReactNativeVideocast.stopScan();
}





export {
    scan, setCurrentURI,play,pause,next,previous,stopScan
}
