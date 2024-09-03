<template>
	<div class="post_detail">
		<el-container>
		    <el-header>
				<el-page-header :icon="ArrowLeft" @back="close_this_page()">
					<template #content>
						<div class="title_bar">
							<div class="title_info">
								<el-avatar :size="32" class="author_avatar" :src="get_avatar()"></el-avatar>
								<span style="font-size: large;font-weight: 600;margin-right: 3px;">{{post.title}}</span>
								<span style="font-size: small;margin-left: 5px;margin-right: 3px;;">by {{author.nickname}}</span>
								<el-tag style="margin-left: 10px;" :type="SetGet(post.type)">{{post.type}}</el-tag>
							</div>
						</div>
					</template>
					<template #extra>
					    <div class="flex items-center" style="display: flex;align-items: center;">
					        <el-text style="margin-right: 10px;"><el-icon><View /></el-icon>{{post.visits}}</el-text>
					        <el-button style="width: 100px;" :type="isStar?'danger':'success'" @click="StarPost()" :plain="isStar" :round="isStar">{{ isStar ? "Cancel Star" : "Star！"}}</el-button>
					    </div>
					</template>
				</el-page-header>
				<el-divider />
			</el-header>
		    <el-main>
				<v-md-preview :text="props.post.content"></v-md-preview>
			</el-main>
		    <el-footer>Footer</el-footer>
		</el-container>
	</div>
</template>

<script setup>
	import { ref, onMounted } from 'vue';
	import { ArrowLeft,View } from '@element-plus/icons-vue'
	import axios from 'axios';
	
	import cathead  from '../assets/cathead.jpg'
	import { ElMessage, ElNotification } from 'element-plus';
	
	const props = defineProps({
		post: {
			type: Object,
			required: true
		},
		author: {
			type: Object,
			required: true
		}
	});
	
	const isStar = ref(false)
	const emit = defineEmits(["close_page"]);
	
	// 关闭本页面
	const close_this_page = () => {
		emit("close_page");
	}
	
	// 处理头像
	const get_avatar = () => {
		if (props.author.avatar == undefined) {
			return cathead;
		};
		return props.author.avatar;
	}
	
	// 字典
	const SetGet = (tag) => {
		const elTagStyle = ref('');
		switch(tag) {
			case 'Q&A':
				elTagStyle.value = "primary";
				break;
			case '技术':
				elTagStyle.value = "success";
				break;
			case '感情':
				elTagStyle.value = "danger";
				break;
			case '喵喵喵':
				elTagStyle.value = "warning";
				break;
			case '灌水':
				elTagStyle.value = "info";
				break;
		}
		return elTagStyle.value;
	}
	
	const StarPost = async () => {
	  try {
	    const postId = parseInt(props.post.id);
	    const url = isStar.value ? "/user/CancelStar" : "/user/postStar";
	    const successMessage = isStar.value ? "Cancel Star success！" : "Star success！";
	
	    const response = await axios.post(url, {post_id: postId} , {
	      headers: {
	        "Content-Type": "application/json"
	      }
	    });
	
	    if (response.data === "success") {
	      NotificationFun(response.data, successMessage);
	      isStar.value = !isStar.value; 
	    }
	  } catch (e) {
	  	NotificationFun("error", e);
	  }
	};

	
	const ViewThis = async () => {
		try {
			// 发送 JSON 对象，并设置 Content-Type 为 'application/json'
			const response = await axios.post("/api/read_post", { post_id: props.post.id }, {
				headers: {
					"Content-Type": "application/json"
				}
			});
	
			const res = await axios.post("/user/isStar", { post_id: props.post.id }, {
				headers: {
					"Content-Type": "application/json"
				}
			});
	
			isStar.value = res.data;
		} catch (error) {
			null;
		}
	};

	const NotificationFun = (errType, text) => {
		ElNotification({
		    title: errType,
		    message: text,
		    type: errType,
			position: 'bottom-right',
		})
	}
	onMounted(() => {
		ViewThis();
	})
</script>

<style>
	.post_detail {
		position: fixed;
		left: 0;
		top: 0;
		background-color: #ffffff;
		width: 100%;
		height: 100%;
		z-index: 20;
		overflow: auto;
	}
	.el-page-header {
		margin-top: 20px;
		margin-bottom: 20px;
		/* border-bottom: 1px solid #e3e3e3; */
	}
	.title_bar {
		display: flex;
		width: 700px;
	}
	.title_info {
		display: flex;
		align-items: center;
		margin-right: 10px;
	}
	.author_avatar {
		margin-right: 10px;
	}
	.title_bar .el-button {
		position: absolute;
		right: 0px;
	}
</style>