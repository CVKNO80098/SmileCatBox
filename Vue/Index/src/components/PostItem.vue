<!-- src/components/PostItem.vue -->
<template>
  <el-card shadow="hover" @click="openPost(post.id)">
	<template #header>
		<ul class="post_title">
			<li><el-avatar :size="59" :src="author_avatar"></el-avatar></li>
			<li style="margin-left: 20px;">
				<ul>
					<li><h3 style="margin-right: 20px;">{{ post.title }}</h3> <el-tag :type="SetGet(post.type)">{{post.type}}</el-tag> </li><br/>
					<li>
						<p class="subtitle">
							<strong>@{{ author.nickname }}</strong>
							<span v-if="post.target_date">  will be in  {{ post.target_date.slice(0,10) }}</span>
						</p>
					</li>
				</ul>
			</li>
			<li class="Time"><ul style="text-align: right; display: grid; align-items: center;">
				<li style="margin-bottom: 7px;">{{post.time.slice(0,10)}}</li>
				<li class="moreRInfo"><el-icon><View /></el-icon>{{post.visits}}</li>
				<li class="moreRInfo"><el-icon><Star /></el-icon>{{post.star_num}}</li>
			</ul></li>
		</ul>
	</template>
    <p></p>
    <el-text class="postContent" line-clamp="1" truncateds>
		{{ post.content }}
	</el-text>
  </el-card>
  <br />
  <PostDetailVue v-if="show_detail" :post="post" :author="author" @close_page="close_detail()"></PostDetailVue>
</template>

<script setup>
import { ref, inject, onMounted } from 'vue';
import cathead  from '../assets/cathead.jpg';
import PostDetailVue from './PostDetail.vue';
import { ElTag } from 'element-plus';
import { View, Star } from '@element-plus/icons-vue'
import axios from 'axios';
	
const props = defineProps({
	post: {
		type: Object,
		required: true
	}
});
// 查看详情
const show_detail = ref(false);
const emit = defineEmits(["no_show", "to_show"])
const openPost = (id) => {
	// const url = `/post/${id}`;
	// window.open(url, '_blank');
	show_detail.value = true;
	emit("no_show", props.post.id);
};
const close_detail = () => {
	show_detail.value = false;
	emit("to_show");
}

// 获取帖子的作者信息
const author = ref('')
const author_avatar = ref('')
const getauthor = async() => {
	try {
		const response = await axios.post(`/user/userInfo`, props.post.author, {
			headers: {
				"Content-Type": "application/json"
			}
		});
		author.value = response.data
		console.log("获取用户信息成功")
	} catch(error) {
		console.error("Error fetching posts:", error);
	}
}
// 处理头像
const get_avatar = () => {
	getauthor();
	if (author.avatar == undefined) {
		author_avatar.value = cathead;
	} else {
		console.log("获取用户头像成功")
		author_avatar.value = author.avatar;
	}
}
// 显示tag的分类
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
onMounted(() => {
	get_avatar();
});
</script>

<style scoped>
	ul {
		list-style: none;
		padding: 0;
	}
p {
	display: inline;
}
p.subtitle{
	font-weight: 400;
	color: #919191;
	font-style: italic;
}
strong {
	font-weight: 400;
}
li h3 {
	display: inline;
	margin: 0;
}
.post_title {
	display: flex;
	/* margin-left: 58px; */
}
.Time {
	color: #919191;
	margin-left: auto;
}
.moreRInfo {
	display:flex;
	flex-direction:row;
	justify-content: end;
	align-items:center;
	font-size: 15px;
	line-height: 20px;
	color: #c9c9c9;
}
.postContent {
	width: 75%;
}
</style>
