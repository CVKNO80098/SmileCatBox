<!-- src/components/PostList.vue -->
<template>
  <div>
	<el-backtop :right="100" :bottom="100" />
	<!-- 主页 -->
	<el-container>
		<el-aside width="23%">
			<asideViewVue @postAdded = "emitApp"></asideViewVue>
		</el-aside>
		<el-main>
			<el-carousel height="150px" trigger="click">
				<el-carousel-item v-for="(item, index) in items" :key="index">
					<img :src="item.img" :alt="item.text" :title="item.author" style="width: 100%; height: auto;"/>
					<h3 class="carousel-text" text="2xl">{{ item.text }}</h3>
				</el-carousel-item>
			</el-carousel>
			<ul class="PostList">
				<PostItem v-for="post in posts" :key="post.id" :post="post" @no_show="hideThisPage" @to_show="showThisPage"/>
			</ul>
			<div class="pagination-container">
				<el-pagination
					style="width: 100%;;margin-left: auto;margin-right: auto;"
					@current-change="handlePageChange"
					:current-page="currentPage"
					:page-size="pageSize"
					:total="totalPosts"
					layout="total, prev, pager, next, jumper"
				/>
			</div>
		</el-main>
	</el-container>
  </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import axios from 'axios';
import {ElNotification} from "element-plus";
import PostItem from './PostItem.vue';
import PostFormVue from './PostForm.vue';
import asideViewVue from './asideView.vue';

import defaultCarouselImg1 from "../assets/carousel/th1.jpg"
import defaultCarouselImg2 from "../assets/carousel/th2.jpg"
import defaultCarouselImg3 from "../assets/carousel/th3.jpg"

const showAdd = ref(false);
const openWhat = ref(0);
const show_body = ref(true);
const items = ref([]);
const posts = ref()
const currentPage = ref(1);  // 当前页码
const pageSize = ref(20);  // 每页数量
const totalPosts = ref(0);  // 帖子总数

const showInput = () => {
	showAdd.value = !showAdd.value;
}

const emitApp = () => {
	fetchPosts();
}

const showThisPage = () => {
	show_body.value = true;
}

const hideThisPage = (num) => {
	show_body.value = false;
	openWhat.value = num;
}

const handlePageChange = (page) => {
	currentPage.value = page;
	fetchPosts(page);
}


const carouselList = async() => {
	try {
		const response = await axios.post(`/api/Carousel`);
		items.value = response.data;
		return items;
	} catch (error) {
		console.error("Error get slide List", error);
		NotificationError(error, "轮播图加载失败，若多次遇到此问题请联系后台，非常感谢！！");
		items.value = [
			{
				img: defaultCarouselImg1,
				text: "当年不肯嫁春风，无端却被秋风误。",
				author: "贺铸 《踏莎行·杨柳回塘》"
			}, {
				img: defaultCarouselImg2,
				text: "两个人从监狱的窗户往外看，一个看见了土地，一个看见了星星。",
				author: "佛雷迪克·朗格布里奇《不灭之诗》: Two men look out through the same bars: One sees the mud and one the stars. "
			}, {
				img: defaultCarouselImg3,
				text: "天若有情天亦老，人间正道是沧桑。",
				author: "毛泽东 《七律·人民解放军占领南京》"
			}
		];
	}
}

const fetchPosts = async() => {
	// 给个默认值
	posts.value = [
		        {
		          id: "1",
		          overhead: 0,
		          title: "First Post",
		          content: "# 你好~\n## 这是我们网站的第一个帖子~ \n`开心~开心~`  \n\n我将在这里测试v-md-editor的行为是否正确，当然，如果您不小心看到啦，也可以作为您编写MarkDown文件的参考数据，在本篇中，您会知道这个东西能解析什么和不能解析什么 \n\n:::warning\n请注意，在本Preview中，分段尽量使用空行法而不是两个空格，原因如下：\n:::\n\n两个空格分段方法  \n喵~\n\n空一行分隔法\n\n喵~\n\n## 文字测试 \n\n~~划掉~~  \n<p style=\"color:red\">html测试</p>  \n\n 写个`代码`试试 \n```python\n print(\'hello,world\')\n```\n\n*斜体文本*\n_斜体文本_\n**粗体文本**\n__粗体文本__\n***粗斜体文本***\n___粗斜体文本___\n\n\n### 分割线 \n\n***\n* * *\n*****\n- - -\n----------\n\n### 文字脚注\n\n是 [^喵喵喵]\n[^喵喵喵]: 是我的老婆！\n\n:::danger\n文本脚注不可用\n:::\n\n区块测试\n > 区块引用\n > 喵喵喵\n > 吃掉！\n\n[链接](https://www.baidu.com)\n\n 图片：\n ![嗷呜~](https://img.picui.cn/free/2024/08/20/66c3e7de29fd6.jpg \"耶耶耶\")\n\n测试表格 \n|  表头   | 表头  |\n|  ----  | ----  |\n| 单元格  | 单元格 |\n| 单元格  | 单元格 |\n\n```mermaid  \ngraph LR \nA --- B \nB-->C[fa:fa-ban forbidden] \nB-->D(fa:fa-spinner); \n\`\`\`\n :::danger\n测试界面中流程图不可用，但正文可用\n:::\n\n",
		          time: "2024-08-11T18:15:30",
		          author: 123,
		          target_date: "2024-09-01T00:00:00",
		          cover: "cover1.jpg",
		          visits: 100,
		          star_num: 10,
		          type: "Q&A"
		        },
		        {
		          id: "2",
		          overhead: 1,
		          title: "Second Post",
		          content: "This is the second post content.",
		          time: "2024-08-12T19:25:45",
		          author: 124,
		          target_date: "2024-09-05T00:00:00",
		          cover: "cover2.jpg",
		          visits: 150,
		          star_num: 20,
		          type: "喵喵喵"
		        },
		        {
		          id: "3",
		          overhead: 1,
		          title: "Third Post",
		          content: "This is the Third post content.",
		          time: "2024-08-12T19:25:45",
		          author: 124,
		          cover: "cover2.jpg",
		          visits: 150,
		          star_num: 20,
		          type: "技术"
		        },
				{
				  id: "4",
				  overhead: 0,
				  title: "Four Post",
				  content: "This is the Four post content.",
				  time: "2024-08-12T19:25:45",
				  author: 124,
				  cover: "cover2.jpg",
				  visits: 150,
				  star_num: 20,
				  type: "技术"
				}
	];
		
	// 可以覆盖哎
	try {
		const response = await axios.post(`/api/posts/${currentPage.value}`)
		posts.value = response.data.posts;
		totalPosts.value = response.data.total;
	} catch (error) {
		console.error("Error fetching posts:", error);
		NotificationError(error, "请几分钟之后重试，您现在看到的是示例界面");
	}
}
const NotificationError = (errMessage, text) => {
	ElNotification({
	    title: 'Error',
	    message: errMessage+text,
	    type: 'error',
	})
}
onMounted(() => {
	document.title = '发现~';
	fetchPosts();
	carouselList();
});
</script>

<style scoped>
	
	@font-face {
		font-family: "ShangShouHuaGuangTi";
		src: url(../assets/fonts/No.337-ShangShouHuaGuangTi-2.ttf);
	}
	
	ul.PostList {
		list-style: none;
		padding: 0;
		justify-content: center;
	}
	.index li:nth-child(1) {
		padding-right: 1%;
		margin-right: 1%;
		border-right: 1px solid #c0c0c0;
		justify-self: right;
	}
	.index li:nth-child(2) {
		background-color: #f6d3ff;
		min-height: 100%;
		flex:1;
		max-width: 800px;
	}
	.index li:nth-child(3) {
		padding-left: 1%;
		margin-left: 1%;
		border-left: 1px solid #c0c0c0;
		justify-self: left;
	}
	/* 走马灯的 */
	.demonstration {
	  color: var(--el-text-color-secondary);
	}
	
	.el-carousel__item {  
	    display: flex;  
	    align-items: center; /* 垂直居中 */  
	    justify-content: center; /* 水平居中 */  
	    height: 150px; /* 或者您希望的任何固定高度 */  
	    width: 100%; /* 如果需要的话，通常.el-carousel__item的宽度已经由外部容器控制 */  
	    overflow: hidden; /* 如果图片比容器大，防止溢出 */  
	}
	
	.el-carousel__item h3 {
		text-shadow: 1px 1px 2px black;
		margin: 0;
	}
	
	.el-carousel__item img {
		position: relative;
		z-index: 1;
	}

	.carousel-text {
		top: 50%;
		display: flex;
		position: absolute;
		text-align: center;
		left: 50%;
		
		font-family: "ShangShouHuaGuangTi", sans-serif;
		
		transform: translate(-50%, -50%); /* 完全居中 */
		font-weight: 100;
		font-size: 30px;
		justify-content: center;
		z-index: 10;
		color: #fff;
	}
</style>
