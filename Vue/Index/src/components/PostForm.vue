<!-- src/components/PostForm.vue -->
<script setup>
import { reactive, ref, computed, inject } from 'vue';
import axios from 'axios';
import { useAutocomplete } from '../composables/useAutocomplete';
import { ElMessage, ElNotification } from 'element-plus'

// 辅助填充
const { type, querySearch, handleSelect} = useAutocomplete();
// 功能functions
const title = ref('');
const content = ref('');
const datatime = ref('');
// 可见性控制
const isVisible = ref(false);

const emit = defineEmits({
	postAdded: null,
	backMain: null,
});

// 计算属性，用于生成显示的错误消息
const errorMessage = ref('');
// 计算属性，用于检查任一变量是否为空
const showError =  () => {
  if (!title.value) {
    errorMessage.value = '标题不能为空';
	open(errorMessage.value, 'error')
  } else if (!type.value) {
    errorMessage.value = '类型不能为空';
	open(errorMessage.value, 'error')
  }
  return '';
};

// 消息通知
const open = (msg, type) => {
  ElMessage({
    message: msg,
    grouping: true,
    type: type,
  })
}


const addPost = () => {
	showError();
	if((title.value&&type.value)){
		const timestamp = new Date().toISOString();
		const newPost = { title: title.value, type: type.value, content: content.value, time: timestamp, target_date: datatime.value };
		
		axios.post(`/api/new_post`, newPost)
			.then(() => {
			emit('postAdded');
			title.value = '';
			type.value = '';
			content.value = '';
		})
		.catch(error => {
		  console.error('Error adding post:', error);
		  NotificationError(error);
		});
	}
};

// 上传图片到服务器的函数
const handleUploadImage = async (event, insertImage, files) => {
  try {
    // 假设你有一个上传图片的 API
    const formData = new FormData();
    formData.append('file', files[0]); // 假设只有一个文件
	
	const url = inject("serverUrl", '');
    const response = await fetch(`${url}/api/upload-image`, {
      method: 'POST',
      body: formData,
    });

    if (!response.ok) throw new Error('上传失败');

    const result = await response.json();
    insertImage({
      url: result.url, // 服务器返回的图片 URL
      desc: result.desc || '', // 服务器返回的图片描述
    });

  } catch (error) {
    console.error('图片上传出错:', error);
    alert('图片上传失败，请重试。');
  }
};

const NotificationError = (errMessage) => {
  ElNotification({
    title: 'Error',
    message: errMessage+"请几分钟之后重试",
    type: 'error',
  })
}

// 美化     good
const back = () => {
	emit("backMain")
	console.log("back");
}
</script>

<template>
	<div class="background" @click="back">
		<!-- <el-alert v-show="errorMessage" :title="errorMessage" type="error" :closable='false' show-icon /> -->
		<div class="inputBlock" @click.stop>
			<div class="inBox">
				<form style="margin-top: 50px;width: 100%;">
					<el-input 
						size='large'
						v-model="title" placeholder="请输入标题"/><br/><br/><br/>
					<el-autocomplete
						size='large'
						v-model="type"
						:fetch-suggestions="querySearch"
						clearable
						placeholder="请输入标签"
						@select="handleSelect"/><br />
					
					<ul class="dataBox">
						<li><label for="target_time" class="target_time"><input id="target_time" type="checkbox" v-model="isVisible"/> 选择事件目标时间</label></li>
						<li style="align-self: center;">
							<el-date-picker
								v-if="isVisible" 
								v-model="datatime"
								type="datetime"
								placeholder="Select date and time"
								:shortcuts="shortcuts"
							/>
						</li>
					</ul>
					
					<br />
					<div class="v-md-editor-BLOCK">
						<v-md-editor 
						height="350px" 
						left-toolbar="undo redo clear | h bold table image todo-list tip emoji code"
						v-model="content" 
						:disabled-menus="[]" 
						@upload-image="handleUploadImage"
						></v-md-editor>
					</div>
					<el-row>
						<el-col :span="8" :offset="8">
							<el-button type="primary" @click="addPost()">Post</el-button>
						</el-col>
					</el-row>
				</form>
			</div>
		</div>
	</div>
</template>

<style scoped>
	/* 被触发显示的东西 */
:deep(.el-alert) {
	position: absolute;
	width: 30%;
	top: 30px
}
	/* 普通页面显示内容 */
:deep(.el-input) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}
:deep(.el-button) {
	width: 100%;
}
.inputBlock {
	justify-content: center;
}
#target_time {
	height: 40px;
	outline-style: none;
	border-width: 0;
	border-radius: 10px;
	width: 85%;
	padding: 3px 0;
	margin-top: 5px;
	font-size: 15px;
	padding-left: 20px;
}
#target_time:checked {
	accent-color: #3385ff;
}
.v-md-editor-BLOCK{
	text-align: left;
}
button {
	position: inherit;

	bottom: 0; 
	width: 100%;
	height: 40px;
}
.inBox {
	display: flex;
	width: 80%;
}
.dataBox {
	display: flex;
	list-style: none;
	justify-content: space-between;
}
.dataBox li {
	width: 300px;
}
.background {
	display: flex;
	align-items: center;
	justify-content: center;
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.2);
	z-index: 99;
}
.inputBlock {
	width: 1000px;
	border-radius: 10px;
	background-color: #f3f3f3;
	height: 700px;
	display: flex;
	justify-content: center;
}
.target_time {
	display: flex;
	justify-content: left;
	align-items: center;
}
#target_time {
	width: auto;
}
</style> 
<!-- 啊这个就是注释哦哇 -->
<!-- 这一行是用来捣乱的
 哇~~~~~~~~~~~~#吃掉！！！！-->