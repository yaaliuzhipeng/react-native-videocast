//
//  UpnpConstants.m
//  example
//
//  Created by lzp on 2021/8/24.
//

#import "UpnpConstants.h"

NSString *const UpnpAVTransport1 = @"urn:schemas-upnp-org:service:AVTransport:1";
NSString *const MulticastSocketRequestHeader = @"M-SEARCH * HTTP/1.1";
NSString *const MAN = @"ssdp:discover";
NSString *const MulticastSocketHost = @"239.255.255.250";
int const MulticastSocketPort = 1900;
NSString *const SymNewLine = @"\r\n";
NSString *const SymSepa = @": ";
NSString *const SymQuote = @"\"";
NSString *const SymMAN = @"MAN";
NSString *const SymMX = @"MX";
NSString *const SymHOST = @"HOST";
NSString *const SymST = @"ST";
NSString *const SymNT = @"NT";
NSString *const SymLocation = @"Location";
NSString *const AcNext = @"Next";
NSString *const AcPause = @"Pause";
NSString *const AcPlay = @"Play";
NSString *const AcPrevious = @"Previous";
NSString *const AcSetAVTransportURI = @"SetAVTransportURI";

@implementation UpnpConstants
@end
