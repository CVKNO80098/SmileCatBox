<template>
  <div class="common-layout">
    <el-container>
		<el-header>
			<IndexMenuVue @selectPage = "updateView"/>
		</el-header>
		<el-main>		  
			<CalendarPageVue v-if="indexNum==1"/>
			<PostListVue v-else-if="indexNum==2"/>
			<NoticePageVue v-else-if="indexNum==4"/>
			<AboutPageVue v-else-if="indexNum==6"/>
		</el-main>
		<el-footer>
			<IndexFooterVue />
		</el-footer>
    </el-container>
  </div>
</template>

<script setup>
	import IndexMenuVue from './IndexMenu.vue';
	
	import CalendarPageVue from './CalendarPage.vue';
	import PostListVue from './PostList.vue';
	import NoticePageVue from './NoticePage.vue';
	import AboutPageVue from './AboutPage.vue';
	
	import IndexFooterVue from './IndexFooter.vue';
	import { ref, onMounted, inject } from 'vue';
	
	const posts = ref([]);
	const indexNum = ref([]);
	

	const updateView = (activeIndex) => {
		indexNum.value = activeIndex[activeIndex.length - 1];
		console.log("切换到页面：",typeof activeIndex,activeIndex, "::", activeIndex[activeIndex.length - 1])
		
		if (indexNum.value == "5-4") {
			window.location.href = "/delete-cookies";
		}
	}
	
;
</script>

<style>
	.el-main {
		min-height: calc(100vh - 80px);
	}
</style>