    // === 第三部分：填入表单 ===

    console.log('提取完成，开始填入表单。提取到的数据:', extractedData)
    let filledCount = 0

    Object.keys(extractedData).forEach(key => {
        if (extractedData[key] && (resumeForm as any)[key] !== undefined) {
            // 只填入空字段，保留用户已填写的内容
            if (!(resumeForm as any)[key] || (resumeForm as any)[key].trim() === '') {
                (resumeForm as any)[key] = extractedData[key]
                filledCount++
                console.log(`成功填入字段 ${key}`)
            } else {
                console.log(`跳过字段 ${key}，已有内容`)
            }
        }
    })

    // 如果没有提取到项目经历，则将未匹配到的大段文本放入项目经历
    if (!extractedData.projectExperience) {
        // 收集未被匹配到的文本行
        const unmatchedLines: string[] = []
        
        // 过滤出较长的文本行（超过一定长度的行）
        lines.forEach(line => {
            // 排除只包含标题关键词的行
            const isTitleLine = Object.values(titleMappings).some(titles => 
                titles.some(title => line.includes(title))
            )
            
            // 排除只包含关键词的行
            const isKeywordLine = line.includes('熟悉') || line.includes('熟练') || 
                                 line.includes('了解') || line.includes('掌握') || 
                                 line.includes('奖')
            
            // 如果不是标题行且不是关键词行，且长度足够长，则认为是未匹配的大段文本
            if (!isTitleLine && !isKeywordLine && line.length > 10) {
                unmatchedLines.push(line)
            }
        })
        
        // 如果有未匹配的文本，将其放入项目经历
        if (unmatchedLines.length > 0) {
            // 限制数量，避免过多内容
            const limitedLines = unmatchedLines.slice(0, 20)
            extractedData.projectExperience = limitedLines.join('\n')
            extractedCount++
            console.log('将未匹配文本放入项目经历:', extractedData.projectExperience)
        }
    }

    // 如果没有提取到姓名，尝试从文件名获取
    if (!extractedData.name && fileInput.value?.files?.[0]) {
        const fileName = fileInput.value.files[0].name.replace(/\.(pdf|doc|docx)$/i, '')
        if (fileName.length <= 10 && /[\u4e00-\u9fa5]/.test(fileName)) {
            if (!resumeForm.name || resumeForm.name.trim() === '') {
                resumeForm.name = fileName
                filledCount++
                console.log('从文件名提取姓名:', fileName)
            }
        }
    }

    // 显示提取结果
    if (filledCount > 0) {
        Message.success(`成功提取并填入 ${filledCount} 个字段的信息`)
    } else if (extractedCount > 0) {
        Message.info('已提取到信息，但相关字段已有内容，未覆盖')
    } else {
        Message.warning('未能从文档中提取到有效信息，请手动填写')
    }