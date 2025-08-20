import { createApp, h } from 'vue'
import MessageComponent from './Message.vue'

export interface MessageOptions {
  message: string
  type?: 'success' | 'warning' | 'error' | 'info'
  duration?: number
}

class MessageManager {
  private instances: Array<{ app: any; container: HTMLElement; id: string }> = []
  private seed = 1

  show(options: MessageOptions | string) {
    const opts = typeof options === 'string' ? { message: options } : options
    const id = `message_${this.seed++}`
    
    const container = document.createElement('div')
    container.id = id
    document.body.appendChild(container)

    const app = createApp({
      render() {
        return h(MessageComponent, {
          ...opts,
          id,
          onClose: () => this.close(id),
          onDestroy: () => this.destroy(id)
        })
      }
    })

    app.mount(container)
    this.instances.push({ app, container, id })

    return {
      close: () => this.close(id)
    }
  }

  close(id: string) {
    // 触发组件关闭动画，实际销毁在destroy方法中
  }

  destroy(id: string) {
    const index = this.instances.findIndex(item => item.id === id)
    if (index > -1) {
      const { app, container } = this.instances[index]
      app.unmount()
      if (document.body.contains(container)) {
        document.body.removeChild(container)
      }
      this.instances.splice(index, 1)
    }
  }

  success(message: string) {
    return this.show({ message, type: 'success' })
  }

  warning(message: string) {
    return this.show({ message, type: 'warning' })
  }

  error(message: string) {
    return this.show({ message, type: 'error' })
  }

  info(message: string) {
    return this.show({ message, type: 'info' })
  }
}

export const Message = new MessageManager()
export default Message
