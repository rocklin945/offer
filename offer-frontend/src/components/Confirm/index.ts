import { createApp, h } from 'vue'
import ConfirmComponent from './Confirm.vue'

export interface ConfirmOptions {
  title?: string
  message: string
  confirmText?: string
  cancelText?: string
  type?: 'warning' | 'danger' | 'info'
}

class ConfirmManager {
  show(options: ConfirmOptions | string): Promise<boolean> {
    const opts = typeof options === 'string' ? { message: options } : options
    
    return new Promise((resolve) => {
      const container = document.createElement('div')
      document.body.appendChild(container)

      const app = createApp({
        render() {
          return h(ConfirmComponent, {
            ...opts,
            onConfirm: () => {
              cleanup()
              resolve(true)
            },
            onCancel: () => {
              cleanup()
              resolve(false)
            }
          })
        }
      })

      const cleanup = () => {
        app.unmount()
        if (document.body.contains(container)) {
          document.body.removeChild(container)
        }
      }

      app.mount(container)
    })
  }

  warning(message: string, title?: string) {
    return this.show({ message, title, type: 'warning' })
  }

  danger(message: string, title?: string) {
    return this.show({ message, title, type: 'danger' })
  }

  info(message: string, title?: string) {
    return this.show({ message, title, type: 'info' })
  }
}

export const Confirm = new ConfirmManager()
export default Confirm