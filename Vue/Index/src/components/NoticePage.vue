<template>
	<div v-if="showNoticeList" class="noticePage">
		

		
		<el-row :gutter="20">
			<el-col :span="16" :offset="4">
				
				<!-- 统计界面 -->
				<el-row :gutter="10">
					<el-col :span="8">
						<el-statistic title="共计" :value="notice_length"/>
					</el-col>
					<el-col :span="8">
						<el-statistic :value="unread_length">
							<template #title>
								<div style="display: inline-block;align-items: center;">
									未读
									<el-icon style="margin-left: 4px;" :size="12">
										<Warning />
									</el-icon>
								</div>
							</template>
						</el-statistic>
					</el-col>
					<el-col :span="8">
						<el-statistic :value="read_length">
							<template #title>
								<div style="display: inline-block;align-items: center;">
									已读
									<el-icon style="margin-left: 4px;" :size="12">
										<CircleCheck />
									</el-icon>
								</div>
							</template>
						</el-statistic>
					</el-col>
				</el-row>
				
				<ul>
					<el-card class="noticeItem" v-for="(notice, index) in notices" :key="index">
						<template #header>
							<div style="display: flex;width: 100%;">
								<span>{{notice.type}}</span>
								<el-button color="#ebb719" style="margin-left: auto;" v-if="notice.is_read==='false'" :disabled="notice.user_id==0" @click="isRead" plain>
									<el-icon><Watermelon /></el-icon>
									{{notice.user_id==0?"紧急通知无法已读":"已读"}}
								</el-button>
								
							</div>
						</template>
						<v-md-preview :text="notice.message"></v-md-preview>
					</el-card>
				</ul>
			</el-col>
		</el-row>
	</div>
	
	<div v-show="!showNoticeList">
		<el-empty description="您还没有消息哦" />
	</div>
</template>

<script setup>
	import axios from 'axios';
	import { ref, onMounted } from 'vue';
	import { CircleCheck, Warning, Watermelon } from '@element-plus/icons-vue'
	import { ElNotification } from 'element-plus';
	
	const notices = ref([]);
	const unread_length = ref(0);
	const read_length = ref(0);
	const notice_length = ref(0);
	const showNoticeList = ref(true);
	//TODO: 调用端口"/user/notice"
	
	const fetchNotice = async() => {
		try {
			const response = await axios.post("/user/notice")
			notices.value = response.data;
		} catch (e) {
			notices.value = [
				{
					user_id: 0,
					type: "通知1",
					message: "123456",
					is_read: "false",
				},
				{
					user_id: 1,
					type: "通知2",
					message: "123456",
					is_read: "false",
				},
				{
					user_id: 1,
					type: "通知3",
					message: "123456",
					is_read: "true",
				},
			];
			NotificationFun("error", "通知获取失败，请刷新，如果多次失败请通知管理员")
		}
		if (notices.value.length == 0) {
			showNoticeList.value = !showNoticeList.value;
		}
		refresh()
	}
	
	const isRead = async (id) => {
		try {
			await axios.post(`/user/notice/read`, id, {
				headers: {
					"Content-Type": "application/json"
				}
			});
		} catch (e) {
			console.error("标记已读失败："+e)
			NotificationFun("error", "已读失败，请检查网络情况，如果多次出现请联系管理人员")
		}
		refresh()
	}
	
	const refresh = () => {
		unread_length.value = 0;
		read_length.value = 0;
		for (var i = 0; i < notices.value.length; i++) {
			if (notices.value[i].is_read == "true") {
				read_length.value++;
			} else if (notices.value[i].is_read == "false") {
				unread_length.value++;
			}
		}
		notice_length.value = notices.value.length;
	}
	
	const NotificationFun = (errType, text) => {
		ElNotification({
		    title: errType,
		    message: text,
		    type: errType,
			// position: 'bottom-right',
			offset: 200,
		})
	}
	onMounted(() => {
		fetchNotice();
	})
</script>

<style>
	.noticeItem {
		margin-bottom: 10px;
	}
	.el-col .el-statistic {
		text-align: center;
	}
	.v-md-editor-preview .github-markdown-body {
		padding: 0;
	}
</style>