import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vitejs.dev/config/
export default defineConfig({
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
    format: 'es'
  },
  define: {
    global: 'globalThis'
  }
})
