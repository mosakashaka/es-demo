<template>
    <div id="app">
        <el-header>
            <el-menu
                    mode = "horizontal"
                    background-color = "#76c7f4"
                    text-color = "#000000"
                    active-text-color = "#ffffff"
                    border-bottom = "none"
                    router
            :default-active = "path"
            class = "el-menu-demo">
            <template v-for="(item, index) in nav_menu_data">
                <el-menu-item :index = "item.path" :key = "index">
                    {{item.title}}
                </el-menu-item>
            </template>
            </el-menu>
        </el-header>
        <el-main>
            <!-- 路由出口 -->
            <!-- 路由匹配到的组件将渲染在这里 -->
            <router-view></router-view>
        </el-main>
    </div>
</template>

<script>
    export default {
        name: 'app',
        components: {
        },
        data () {
            return {
                path: '',
                nav_menu_data: [{
                    title: 'Home',
                    path: '/home'
                }, {
                    title: 'About',
                    path: '/about'
                }, {
                    title: 'Files',
                    path: '/files'
                }]
            }
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        color: #2c3e50;
    }
</style>
