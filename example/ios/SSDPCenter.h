//
//  SSDPCenter.h
//  example
//
//  Created by lzp on 2021/8/24.
//

#import <Foundation/Foundation.h>
#import "GCDAsyncUdpSocket.h"
#import "UpnpConstants.h"

@interface SSDPCenter : NSObject<GCDAsyncUdpSocketDelegate>

@property (nonatomic,strong) GCDAsyncUdpSocket* socket;

+(instancetype) shared;
-(void) scan;
@end
