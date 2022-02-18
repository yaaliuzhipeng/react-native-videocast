//
//  SSDPCenter.m
//  example
//
//  Created by lzp on 2021/8/24.
//

#import "SSDPCenter.h"

@implementation SSDPCenter
static SSDPCenter *instance = nil;
+(instancetype) shared
{
  static dispatch_once_t onceToken;
  dispatch_once(&onceToken, ^{
    if(instance == nil) {
      instance = [[super allocWithZone:NULL] init];
    }
  });
  return instance;
}
+ (instancetype)allocWithZone:(struct _NSZone *)zone
{
  return [SSDPCenter shared];
}

- (void) scan
{
  _socket = [[GCDAsyncUdpSocket alloc] initWithDelegate:self delegateQueue:dispatch_get_main_queue()];
//  [self setupSocket];
  NSString *msg = @"M-SEARCH * HTTP/1.1\n"
  @"MAN: \"ssdp:discover\"\n"
  @"MX: 5\n"
  @"HOST: 239.255.255.250:1900\n"
  @"ST: ssdp:all\n";
  
  [_socket sendData:[msg dataUsingEncoding:NSUTF8StringEncoding] toHost:@"239.255.255.250" port:1900 withTimeout:3 tag:11];
  NSError *err = nil;
  
  if(![_socket joinMulticastGroup:MulticastSocketHost error:&err]){
    NSLog(@"加入组播失败");
  }else{
    NSLog(@"加入组播成功");
  }
  if(![_socket beginReceiving:&err]){
    NSLog(@"开始接收失败");
  }else{
    NSLog(@"开始接收");
  }
}

- (void) setupSocket
{
  [_socket setIPv6Enabled:NO];
  NSError *err = nil;
  if(![_socket joinMulticastGroup:MulticastSocketHost error:&err]){
    NSLog(@"加入组播失败");
  }
  if(![_socket beginReceiving:&err]){
    NSLog(@"开始接收失败");
  }
}

/**
 Delegate Methods of GCDAsyncUdpSocketDelegate
 */
- (void) udpSocket:(GCDAsyncUdpSocket *)sock didSendDataWithTag:(long)tag
{
  NSLog(@"发送消息成功");
}
- (void)udpSocket:(GCDAsyncUdpSocket *)sock didNotSendDataWithTag:(long)tag dueToError:(NSError *)error
{
  NSLog(@"错误原因: %@",error.localizedFailureReason);
}
- (void)udpSocketDidClose:(GCDAsyncUdpSocket *)sock withError:(NSError *)error
{
  NSLog(@"关闭、错误: %@",error.localizedFailureReason);
}
- (void) udpSocket:(GCDAsyncUdpSocket *)sock didReceiveData:(NSData *)data fromAddress:(NSData *)address withFilterContext:(id)filterContext
{
  NSString *aStr = [[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding];
  NSLog(@"接收到消息: %@",aStr);
}
- (void)udpSocket:(GCDAsyncUdpSocket *)sock didConnectToAddress:(NSData *)address
{
  NSLog(@"连接到目标地址,%@",[[NSString alloc] initWithData:address encoding:NSUTF8StringEncoding]);
}
- (void)udpSocket:(GCDAsyncUdpSocket *)sock didNotConnect:(NSError *)error
{
  NSLog(@"没有连接成功、%@",error.localizedFailureReason);
}

@end
