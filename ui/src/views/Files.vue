<template>
    <div id="files">
        <el-header>
            <el-button type="success" @click="modalVisible = true">
                上传
            </el-button>
            <el-button type="primary" icon="el-icon-search" @click="searchFile">搜索</el-button>
        </el-header>
        <el-main>
            <el-table
                    :data="tableData"
                    stripe
                    style="width: 100%">
                <el-table-column
                        prop="id"
                        label="Id"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="originalName"
                        label="文件名"
                        >
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        label="上传时间"
                        width="300">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        label="操作"
                        width="120">
                    <template slot-scope="scope">
                        <el-button @click="handleDownload(scope.$index)" type="text" size="small">下载</el-button>
                        &nbsp;
                        <el-popconfirm
                                confirm-button-text='确定'
                                cancel-button-text='取消'
                                icon="el-icon-info"
                                icon-color="red"
                                title="确认要删除该文件吗?"
                                @confirm="handleDelete(scope.$index)"
                        >
                            <el-button slot="reference" type="text" size="small">删除</el-button>
                        </el-popconfirm>
                    </template>
                </el-table-column>
            </el-table>
            <el-row style="margin-bottom: 16px;">
                <el-pagination v-show="total>0"
                               @size-change="searchFile"
                               @current-change="searchFile"
                               :current-page.sync="search.pageNumber"
                               :page-size.sync="search.pageSize"
                               layout="total, sizes, prev, pager, next, jumper"
                               :total="total"
                />
            </el-row>
        </el-main>
        <el-dialog
                title="上传"
                :visible.sync="modalVisible"
                width="50%"
                :before-close="handleClose">
            <el-upload
                    class="upload-demo"
                    :action="uploadUrl"
                    :limit="1"
                    :show-file-list="false"
                    :on-success="handleUploadSuccess"
                    :file-list="fileList">
                <el-button size="small" type="primary">点击上传</el-button>
            </el-upload>
        </el-dialog>
    </div>
</template>

<script>
    import {searchFile, downloadFile, deleteFile} from '@/utils/api'

    export default {
        name: 'Files',
        components: {},
        data() {
            return {
                search: {
                    fileName: '',
                    pageSize: 10,
                    pageNumber: 1
                },
                fileList: [],
                modalVisible: false,
                tableOptions: {},
                tableData: [{

                }],
                total: 0,
                uploadUrl : process.env.VUE_APP_BASE_API + '/file/upload'
            }
        },
        methods: {
            handleClose() {
                this.modalVisible = false
                this.searchFile()
            },
            handleUploadSuccess() {
                this.handleClose();
            },
            handleDownload(idx) {
                let fileId = this.tableData[idx].id
                let fileName = this.tableData[idx].originalName
                downloadFile(fileId).then(response => {
                    let url = window.URL.createObjectURL(new Blob([response]))
                    let link = document.createElement('a')
                    link.style.display = 'none'
                    link.href = url
                    link.setAttribute('download', fileName)
                    document.body.appendChild(link)
                    link.click()
                    link.remove()
                }).catch((err) => {
                    this.$notify.error({
                        title: '错误',
                        message: '下载失败：' + err
                    });
                    console.log(err)
                })
            },
            handleDelete(idx) {
                let fileId = this.tableData[idx].id
                let fileName = this.tableData[idx].originalName
                deleteFile(fileId).then(response => {
                    if (response.success) {
                        this.$notify({
                            title: '提示',
                            message: '删除成功',
                            type: 'success'
                        });
                        this.searchFile()
                    } else {
                        this.$notify({
                            title: '警告',
                            message: '删除失败：' + response.msg,
                            type: 'warning'
                        });
                    }
                }).catch( (err) => {
                    this.$notify.error({
                        title: '错误',
                        message: '删除失败：' + err
                    });
                    console.log(err)
                })

            },
            searchFile() {
                searchFile(this.search).then(response => {
                    this.tableData = response.data.list
                    this.total = response.data.total
                })
            }
        },
        mounted() {
            this.searchFile()
        }
    }
</script>
<style lang="css">
    .el-header {
        text-align: left;
    }
</style>