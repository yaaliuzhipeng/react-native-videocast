
declare function scan(timeout?:number,onDevicesChanged?:(devices:any[]) => any,onError?:(e) => any) {} 
declare function setCurrentURI(uuid:string, uri:string, onSuccess?:any, onError?:any) {} 
declare function play(uuid:string, onSuccess?:any, onError?:(e) =>any){}
declare function pause(uuid:string, onSuccess?:any, onError?:(e) => any){}
declare function previous(uuid:string, onSuccess?:any, onError?:(e) => any){}
declare function next(uuid:string, onSuccess?:any, onError:(e) => any){}
declare function stopScan(){}

export {
    scan, setCurrentURI,play,pause,next,previous,stopScan
}
