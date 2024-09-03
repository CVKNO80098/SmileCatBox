<template>
	<div class="aboutIndex">
		<el-container>
			<el-main>
				<el-timeline style="max-width: 700px;">
					<el-timeline-item 
					v-for="(line, index) in lines" 
					:key="index"
					:icon="line.done=='true'?SuccessFilled:MoreFilled"
					size='large'
					:type="line.done=='true'?'success':'primary'"
					:timestamp="line.time"
					placement="top">
						<el-card>
							<template #header>
							<h4>{{line.title}}</h4>
							</template>
							<p>{{line.detail}}</p>
						</el-card>
					</el-timeline-item>
				</el-timeline>
			</el-main>
			<el-aside width="30%">
				
			</el-aside>
		</el-container>
		
	</div>
</template>

<script setup>
	import { SuccessFilled,MoreFilled } from '@element-plus/icons-vue'
	import axios from 'axios';
	import { getCurrentInstance, inject, onMounted, ref } from 'vue';
	import {ElNotification} from "element-plus";
	
	const lines = ref([])
	
	const getLine = () => {
		axios.get(`/api/history`)
		.then(response => {
			lines.value = response.data;
		})
		.catch(error => {
			console.error("Error fetching posts:", error);
			NotificationError(error);
		})
	}
	const NotificationError = (errMessage) => {
	  ElNotification({
	    title: 'Error',
	    message: errMessage+"请几分钟之后重试",
	    type: 'error',
	  })
	}
	onMounted(() => {
		getLine();
		document.title = "关于"
	})
</script>

<style>
	.aboutIndex {
		margin-left: auto;
		margin-right: auto;
	}
	h4 ,p{
		margin: 0;
	}
	.el-card {
		
	}
</style>