<template>
  <div class="login-page">
    <el-card class="login-card" shadow="never">
      <h2 class="brand">SemiTris 的 ACG 私宅</h2>
      <p class="sub">只属于我的番剧小窝 · 欢迎回来</p>
      <el-form :model="form" @submit.prevent>
        <el-form-item>
          <el-input v-model="form.username" placeholder="账号" size="large" />
        </el-form-item>
        <el-form-item>
          <el-input
            v-model="form.password"
            type="password"
            show-password
            placeholder="密码"
            size="large"
            @keyup.enter="doLogin"
          />
        </el-form-item>
        <el-button
          type="primary"
          size="large"
          class="login-btn"
          :loading="loading"
          @click="doLogin"
        >
          登录
        </el-button>
      </el-form>
      <p class="hint">默认账号：semitris / 123456</p>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { login } from '@/api/user'

const router = useRouter()
const form = reactive({ username: '', password: '' })
const loading = ref(false)

async function doLogin() {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入账号和密码')
    return
  }
  loading.value = true
  try {
    await login({ username: form.username, password: form.password })
    ElMessage.success('登录成功，欢迎回来')
    router.push('/anime')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: calc(100vh - 90px);
}
.login-card {
  width: 360px;
  padding: 12px 20px 20px;
  background: var(--bg-card);
}
.brand {
  text-align: center;
  background: var(--gradient-brand);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
  margin: 12px 0 4px;
}
.sub {
  text-align: center;
  color: var(--text-secondary);
  font-size: 13px;
  margin-bottom: 20px;
}
.login-btn {
  width: 100%;
}
.hint {
  text-align: center;
  color: var(--text-secondary);
  font-size: 12px;
  margin-top: 14px;
}
</style>
