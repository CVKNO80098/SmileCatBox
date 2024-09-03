<template>
	<el-button 
		class="Elbutton"
		@click="showInput"
		type="primary"
		:icon="Upload"
		>发布主题</el-button>
	<PostFormVue v-if="showAdd" @postAdded="emitApp" @backMain="showInput" />
</template>

<script setup>
	import { ref } from 'vue';
	import PostItem from './PostItem.vue';
	import PostFormVue from './PostForm.vue';
	import { Upload } from '@element-plus/icons-vue'
	
	const showAdd = ref(false);
	
	const emit = defineEmits(['postAdded']);
	
	const showInput = () => {
		showAdd.value = !showAdd.value;
	}
	
	const emitApp = () => {
		emit('postAdded');
	}
	
	defineProps({
	  posts: Array
	});
</script>

<style>
	.Elbutton {
		margin-top: 30px;
		width: 100%;
		max-width: 250px;
		margin-left: auto;
		margin-right: auto;
	}
	.demo-tabs > .el-tabs__content {
	  padding: 32px;
	  color: #6b778c;
	  font-size: 32px;
	  font-weight: 600;
	}
	
	.el-tabs--right .el-tabs__content,
	.el-tabs--left .el-tabs__content {
	  height: 100%;
	}
</style>