<template>
	<el-menu
	    :default-active="activeIndex"
	    class="el-menu-demo"
	    mode="horizontal"
	    @select="handleSelect"
		>
	    <el-menu-item index="1"><el-icon><Calendar /></el-icon>日历</el-menu-item>
		<el-menu-item index="2"><el-icon><Connection /></el-icon>发现</el-menu-item>
		<el-menu-item index="4"><el-icon><Bell /></el-icon>通知</el-menu-item>
		<el-sub-menu index="5">
			<template #title><el-icon><User /></el-icon>个人中心</template>
			<el-menu-item index="5-1">修改资料</el-menu-item>
			<el-menu-item index="5-2">我的关注</el-menu-item>
			<el-menu-item index="5-3">设置</el-menu-item>
			<el-menu-item index="5-4">退出登录</el-menu-item>
		</el-sub-menu>
	    <el-menu-item index="6"><el-icon><Flag /></el-icon>关于</el-menu-item>
	</el-menu>
	
	
</template>

<script setup>
	import { ref } from 'vue';
	import { Calendar, Connection, Bell, User, Flag } from '@element-plus/icons-vue';
	const activeIndex = ref('1')
	const emit = defineEmits(['selectPage']);
	const handleSelect = (key, keyPath) => {
		emit('selectPage', keyPath)
	}
</script>

<style>
	.el-menu--horizontal > .el-menu-item:nth-child(0), 
	.el-menu--horizontal > .el-menu-item:nth-child(1),
	.el-menu--horizontal > .el-menu-item:nth-child(2)
	{
	  width: 100px;
	}
	.el-menu--horizontal > .el-menu-item:nth-child(2) {
	  margin-right: auto;
	}

</style>