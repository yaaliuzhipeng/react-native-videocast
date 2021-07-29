import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, Pressable,TouchableOpacity, ActivityIndicator, Platform, StatusBar, ScrollView, SafeAreaView, useWindowDimensions } from 'react-native';
const isAndroid = Platform.OS == 'android';

import { scan, setCurrentURI,play,pause,stopScan,next,previous } from '../libs';


const App = props => {

	const dimens = useWindowDimensions();

	useEffect(() => {
		if (isAndroid) {
			StatusBar.setBackgroundColor('transparent')
			StatusBar.setTranslucent(true);
			StatusBar.setBarStyle('dark-content');
		}
	}, []);

	const [isScanning, toggleScanning] = useState(false);
	const [devices, setDevices] = useState([]);
	const [choosedDevice, setChoosedDevice] = useState();
	const [syncSuccess,setSyncSuccess] = useState(false);


	/**
	 * @function 扫描局域网终端设备
	 */
	const startScan = () => {
		console.log('启动扫描')
		toggleScanning(true);
		scan(-1, (devs) => {
			setDevices(devs);
		});
	}
	/**
	 * @function 停止扫描
	 */
	const _stopScan = () => {
		stopScan();
	}
	/**
	 * @function 设置播放地址
	 */
	const syncVideoURL = () => {
		if (choosedDevice != undefined) {
			setCurrentURI(choosedDevice.uuid, "http://cos-lianailing.hashfun.cn/movies/1/6.mp4",() => {
				setSyncSuccess(true);
			},(e) => {
				console.log(e)
			});
		}
	}
	/**
	 * @function 播放
	 */
	const _play = () => {
		if (choosedDevice != undefined) {
			play(choosedDevice.uuid,() => {},(e) => {console.log(e)});
		}
	}
	/**
	 * @function 暂停
	 */
	const _pause = () => {
		if (choosedDevice != undefined) {
			pause(choosedDevice.uuid,() => {},(e) => {console.log(e)});
		}
	}
	/**
	 * @function 下一集
	 */
	const _next = () => {
		if (choosedDevice != undefined) {
			next(choosedDevice.uuid,() => {},(e) => {console.log(e)});
		}
	}
	/**
	 * @function 上一集
	 */
	const _previous = () => {
		if (choosedDevice != undefined) {
			previous(choosedDevice.uuid,() => {},(e) => {console.log(e)});
		}
	}

	return (
		<View style={[styles.container, styles.center]}>
			<SafeAreaView>
				<ScrollView
					showsVerticalScrollIndicator={false}
					contentContainerStyle={{ paddingVertical: dimens.height * 0.05 }}
				>
					<View style={[styles.center, { marginTop: 30 }]}>
						<Text style={[styles.label]}>{LABEL}</Text>
						<Text style={[styles.author]}>{POWERBY}</Text>
						<View style={styles.divider} />
						<Text style={[styles.author, { marginBottom: 0 }]}>{INTRODUCTION_LABEL}</Text>
						<Text style={[styles.introduction]}>{INTRODUCTION}</Text>

						<View style={styles.divider} />
						<Text style={[styles.author, { marginBottom: 0 }]}>{EXAMPLE_LABEL}</Text>
						<Text style={[styles.step_label]}>{STEP_ONE_LABEL}</Text>
						<TouchableOpacity onPress={startScan} style={[styles.button]}>
							<Text style={[styles.button_label]}>Scan</Text>
						</TouchableOpacity>
						<View style={{ width: '100%', paddingHorizontal: 20 }}>
							<View style={{ flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 10 }}>
								<Text style={styles.available_label}>当前可用设备:</Text>
								{isScanning && <ActivityIndicator size="small" color="#EB2138" />}
							</View>
							{devices.length == 0 && <Text style={{ fontSize: 13, color: '#333' }}>暂无..</Text>}
							{
								devices.map((v, i) => {
									let isHighlight = false;
									if (choosedDevice != undefined) {
										isHighlight = choosedDevice.uuid = v.uuid;
									}
									return <DeviceItem key={v.uuid} onPress={() => {
										setChoosedDevice(v);
									}} name={v.name} isHighlight={isHighlight} />;
								})
							}
						</View>

						<Text style={[styles.step_label]}>{STEP_TWO_LABEL}</Text>
						<TouchableOpacity onPress={syncVideoURL} style={[styles.button]}>
							<Text style={[styles.button_label]}>Sync Video URL</Text>
						</TouchableOpacity>

						<Text style={[styles.step_label]}>{STEP_THREE_LABEL}</Text>
						<View style={{flexDirection:'row',alignItems:'center',marginBottom:20,marginTop:20}}>
							<TouchableOpacity onPress={_play} style={[styles.action_button,{borderTopRightRadius:0,borderBottomRightRadius:0}]}>
								<Text style={[styles.button_label]}>播放</Text>
							</TouchableOpacity>
							<TouchableOpacity onPress={_pause} style={[styles.action_button,{borderTopLeftRadius:0,borderBottomLeftRadius:0}]}>
								<Text style={[styles.button_label]}>暂停</Text>
							</TouchableOpacity>
						</View>
						<View style={{ flexDirection: 'row', alignItems: 'center', justifyContent: 'center' }}>
							<TouchableOpacity onPress={_previous} style={[styles.action_button,{marginEnd:30}]}>
								<Text style={[styles.button_label]}>上一集</Text>
							</TouchableOpacity>
							<TouchableOpacity onPress={_next} style={[styles.action_button]}>
								<Text style={[styles.button_label]}>下一集</Text>
							</TouchableOpacity>
						</View>
						<TouchableOpacity onPress={_stopScan} style={[styles.action_button,{marginTop:20}]}>
							<Text style={[styles.button_label]}>停止扫描</Text>
						</TouchableOpacity>
					</View>
				</ScrollView>
			</SafeAreaView>
		</View>
	);
};

export default App;

const DeviceItem = (props) => {

	return (
		<Pressable onPress={props.onPress} style={{
			flexDirection: 'row',
			alignItems: 'center',
			marginBottom: 8,
			borderWidth: 2,
			borderRadius: 9,
			borderColor: props.isHighlight ? '#35353a42' : 'transparent',
			backgroundColor: props.isHighlight ? '#35353a33' : 'transparent'
		}}>
			<View style={{ padding: 7, borderRadius: 8, backgroundColor: '#35353a' }}>
				<Text style={{ fontSize: 13, fontWeight: 'bold', color: '#FFFFFF99' }}>TV</Text>
			</View>
			<Text style={{ fontSize: 15, color: '#000', marginStart: 8 }}>{props.name}</Text>
		</Pressable>
	)
}

const LABEL = '🐹 VideoCast 🐹';
const POWERBY = '☆☆☆Powered By ⭐️LZP☆☆☆';
const INTRODUCTION_LABEL = "简要说明";
const EXAMPLE_LABEL = "使用示例";
const STEP_ONE_LABEL = "第一步: 启动局域网智能TV终端扫描";
const STEP_TWO_LABEL = "第二步: 选中上面任一可用设备、然后点击同步播放地址";
const STEP_THREE_LABEL = "第三步: 点击播放进行播放";
const INTRODUCTION = `
该 ReactNatve Package 目前只提供Android端功能实现。
核心方法仅有 scan 、play、pause、next
`;


const styles = StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: '#f8f8fa',
	},
	center: {
		justifyContent: 'center',
		alignItems: 'center',
	},
	divider: {
		width: '90%',
		borderBottomWidth: 1,
		borderBottomColor: '#32364B55',
		marginVertical: 23
	},
	label: {
		fontSize: 23,
		fontWeight: 'bold',
		color: '#32364B',
		marginBottom: 5,
	},
	author: {
		fontSize: 16,
		fontWeight: 'bold',
		letterSpacing: 1,
		marginVertical: 5,
		color: '#32364B',
	},
	introduction: {
		fontSize: 16,
		lineHeight: 20,
		letterSpacing: 1,
		color: '#000',
		marginHorizontal: 20,
		textAlign: 'center'
	},
	step_label: {
		fontSize: 14,
		fontWeight: 'bold',
		letterSpacing: 1,
		color: '#32364B',
		alignSelf: 'flex-start',
		marginStart: 20,
		marginTop: 20,
		marginBottom: 10
	},
	button: {
		paddingHorizontal: '20%',
		paddingVertical: 10,
		borderRadius: 16,
		backgroundColor: '#EB2138',
		marginVertical: 10
	},
	action_button: {
		paddingHorizontal: 20,
		paddingVertical: 10,
		borderRadius: 12,
		backgroundColor: '#353535'
	},
	button_label: {
		color: '#FFF',
		fontSize: 16,
		fontWeight: 'bold'
	},
	available_label: {
		fontSize: 14,
		color: '#676767',
	}
});
