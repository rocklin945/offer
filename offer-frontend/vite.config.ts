import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  base: '/', // 确保静态资源用绝对路径
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
    },
  },
  server: {
    port: 3000,
    proxy: {
      '/api': {
        target: 'http://localhost:8081',
        changeOrigin: true,
        rewrite: (path) => path
      }
    }
  },
  optimizeDeps: {
    include: [
      'pdfjs-dist/legacy/build/pdf',
      'pdfjs-dist/build/pdf.worker'
    ],
    exclude: ['pdfjs-dist']
  },
  worker: {
    format: 'es',
    // 确保worker文件有正确的MIME类型
    rollupOptions: {
      output: {
        assetFileNames: (assetInfo) => {
          // 将.mjs文件重命名为.js文件以避免MIME类型问题
          if (assetInfo.name?.endsWith('.worker.js')) {
            return 'assets/[name].[hash].js';
          }
          if (assetInfo.name?.endsWith('.mjs')) {
            return 'assets/[name].[hash].js';
          }
          return 'assets/[name].[hash].[ext]';
        }
      }
    }
  },
  define: {
    global: 'globalThis'
  },
  build: {
    // 确保静态资源有正确的MIME类型
    rollupOptions: {
      output: {
        assetFileNames: (assetInfo) => {
          // 将.mjs文件重命名为.js文件以避免MIME类型问题
          if (assetInfo.name?.endsWith('.mjs')) {
            return 'assets/[name].[hash].js';
          }
          return 'assets/[name].[hash].[ext]';
        }
      }
    }
  }
})