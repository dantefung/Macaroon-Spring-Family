package com.dantefung.okra.log;/*
 *
 *  *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  *  <p>
 *  *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *  <p>
 *  * https://www.gnu.org/licenses/lgpl.html
 *  *  <p>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

import com.dantefung.okra.log.aspect.SysLogAspect;
import com.dantefung.okra.log.event.SysLogListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lengleng
 * @date 2019/2/1
 * 日志自动配置
 */
@EnableAsync
@RequiredArgsConstructor
@ConditionalOnWebApplication
//@Configuration(proxyBeanMethods = false)
@Configuration
@Slf4j
public class LogAutoConfiguration {

	//private final RemoteLogService remoteLogService;

	@Bean
	public SysLogListener sysLogListener() {
		log.info("======>开始自动装配SysLogListener...");
		return new SysLogListener(/*remoteLogService*/);
	}

	@Bean
	public SysLogAspect sysLogAspect() {
		log.info("======>开始自动装配SysLogAspect...");
		return new SysLogAspect();
	}


}
