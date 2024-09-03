<template>
	<div class="normal-footer">
		<el-row :gutter="20">
			<el-col :span="8" :offset="8">
				<div class="more-about">
					<el-space :spacer="spacerInfo">
						<div>泠水 &copy; CVKNO80098@outlook.com</div>
					</el-space>
				</div>
			</el-col>
		</el-row>
	</div>
</template>

<script setup>
	import { ElDivider } from 'element-plus'
	import { h } from 'vue'
	
	const spacerInfo = h(ElDivider, { direction: 'vertical' });
</script>

<style>
	.normal-footer {
		height: 60px;
		color: #d2d2d2;
		background-color: #6f6f6f;
		align-content: center;
		text-align: center;
	}
	.more-about {
		height: 100%;
	}
</style>