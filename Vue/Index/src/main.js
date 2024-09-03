import { createApp } from 'vue'
// import router from './router';
import App from './App.vue'
// MD解析
import VueMarkdownEditor from "@kangc/v-md-editor";
import '@kangc/v-md-editor/lib/style/base-editor.css';
import VMPreview from "@kangc/v-md-editor/lib/preview";
import '@kangc/v-md-editor/lib/style/preview.css';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';

import createTodoListPlugin from '@kangc/v-md-editor/lib/plugins/todo-list/index';
import '@kangc/v-md-editor/lib/plugins/todo-list/todo-list.css';

import createTipPlugin from '@kangc/v-md-editor/lib/plugins/tip/index';
import '@kangc/v-md-editor/lib/plugins/tip/tip.css';

import createEmojiPlugin from '@kangc/v-md-editor/lib/plugins/emoji/index';
import '@kangc/v-md-editor/lib/plugins/emoji/emoji.css';

import createKatexPlugin from '@kangc/v-md-editor/lib/plugins/katex/cdn';

import createMermaidPlugin from '@kangc/v-md-editor/lib/plugins/mermaid/cdn'
import '@kangc/v-md-editor/lib/plugins/mermaid/mermaid.css'
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';

import Prism from 'prismjs';
import hljs from 'highlight.js'

VueMarkdownEditor.use(githubTheme, {
	Hljs:hljs,
	Prism,
});
VMPreview.use(githubTheme, {
	Hljs:hljs,
	Prism,
})

VueMarkdownEditor.use(createTodoListPlugin());
VueMarkdownEditor.use(createTipPlugin());
VueMarkdownEditor.use(createEmojiPlugin());
VueMarkdownEditor.use(createKatexPlugin());
VueMarkdownEditor.use(createLineNumbertPlugin());
VueMarkdownEditor.use(createMermaidPlugin());

VMPreview.use(createTodoListPlugin());
VMPreview.use(createTipPlugin());
VMPreview.use(createEmojiPlugin());
VMPreview.use(createKatexPlugin());
VMPreview.use(createLineNumbertPlugin());
VMPreview.use(createMermaidPlugin());

// 主题引入
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

const app = createApp(App)

app.use(VueMarkdownEditor);
app.use(VMPreview);
app.use(ElementPlus);
// app.use(router);
app.mount('#app');

