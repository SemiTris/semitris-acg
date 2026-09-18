<template>
  <div class="main-layout">
    <el-menu
      mode="horizontal"
      class="top-nav"
      :default-active="$route.path"
      router
    >
      <div class="brand" @click="$router.push('/anime')">
        SemiTris 的 ACG 私宅
      </div>
      <el-menu-item index="/anime">番剧库</el-menu-item>
      <el-menu-item index="/collection">追番</el-menu-item>
      <el-menu-item index="/recommend">安利墙</el-menu-item>
      <el-menu-item index="/login">登录</el-menu-item>
    </el-menu>
    <main class="content">
      <router-view />
    </main>
  </div>
</template>

<script setup>
</script>

<style scoped>
.main-layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background: var(--bg-primary);
}
.top-nav {
  background: var(--bg-secondary);
  border-bottom: none;
}
.brand {
  padding: 0 20px;
  font-weight: 600;
  cursor: pointer;
  color: var(--text-primary);
  background: var(--gradient-brand);
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}
.content {
  flex: 1;
  padding: 24px;
}
</style>
