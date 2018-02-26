# NewVersonApp
发布

使用方法
在MyApplication中onCreate中进行网络监听
OkHttpUtils.initNetService(this);

1.get请求，返回json
OkHttpUtils.get("", new OkHttpUtils.OnRequestCallBack() {
			@Override
			public void onSucceed(String json) {
         //成功
			}

			@Override
			public void onFailure(String error) {
         //失败
			}

			@Override
			public void onNotNet() {
        //无网络状态
			}
		});

2.get请求，返回对象，DataType泛型类型 OBJECT单体对象、ARRAY集合对象
OkHttpUtils.get("", OkHttpUtils.DataType.OBJECT, Dog.class, new OkHttpUtils.OnRequestDataCallBack<Dog>() {
			@Override
			public void onSucceed(Dog data) {
			  //json数据返回的是单体对象时回调
			}

			@Override
			public void onSucceed(List<Dog> datas) {
        //json数据返回的是集合对象时回调
			}

			@Override
			public void onFailure(String error) {
        //失败
			}

			@Override
			public void onNotNet() {
        //无网络状态
			}
		});
   3.get请求，返回特定格式的对象 BaseResponse<T>
   {
     code:1,
     msg:'xx',
     data:{
     }
   }
   OkHttpUtils.get("", new TypeToken<BaseResponse<Dog>>() {
		}, new OkHttpUtils.onRequestCallBacks<BaseResponse<Dog>>() {
			@Override
			public void onSucceed(BaseResponse<Dog> data) {

			}

			@Override
			public void onFailure(String error) {

			}

			@Override
			public void onNotNet() {

			}
		});
    
    4.post请求同get一致 OkHttpUtils.post(""...)
    
    5.上传表单+图片
    Map<String ,String > map = new HashMap<>();
		map.put("name","xxx");
    第一个参数：url，第二个表单数据，第三个对应图片的键参数，第四个图片路径，也可以是图片文件
		OkHttpUtils.upLoadingImage("", map, "img", "mnt/sdcard/aa.jpg", new OkHttpUtils.OnRequestCallBack() {
			@Override
			public void onSucceed(String json) {
				
			}

			@Override
			public void onFailure(String error) {

			}

			@Override
			public void onNotNet() {

			}
		});
    
    6.上传表单+任意文件
    同上
    OkHttpUtils.upLoadingFile(...);
    
    7.文件下载
    OkHttpUtils.download("", "mnt/sdcard/xx.apk", new OkHttpUtils.OnProgressListenter() {
			@Override
			public void onProgress(long totalBytes, long currentBytes, int percent) {
				
			}

			@Override
			public void onFinished(File file, InputStream inputStream) {

			}

			@Override
			public void onFailure(String error) {

			}
		});


