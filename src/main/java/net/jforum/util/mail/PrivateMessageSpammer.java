/*
 * Copyright (c) JForum Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms,
 * with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the
 * following  disclaimer.
 * 2)  Redistributions in binary form must reproduce the
 * above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * 3) Neither the name of "Rafael Steil" nor
 * the names of its contributors may be used to endorse
 * or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT
 * HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE
 *
 * Created on 20/06/2004 03:30:58
 * The JForum Project
 * http://www.jforum.net
 */
package net.jforum.util.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jforum.core.exceptions.MailException;
import net.jforum.entities.User;
import net.jforum.util.ConfigKeys;
import net.jforum.util.JForumConfig;

import org.apache.commons.lang.StringUtils;

/**
 * Notify users that they have new private messages
 *
 * @author Rafael Steil
 */
public class PrivateMessageSpammer extends Spammer {
	public PrivateMessageSpammer(JForumConfig config) throws MailException {
		super(config);
	}

	public void prepare(User user) {
		if (StringUtils.isEmpty(user.getEmail())) {
			return;
		}

		String path = new StringBuilder(128)
			.append(this.buildForumLink())
			.append("pm/inbox")
			.append(this.getConfig().getValue(ConfigKeys.SERVLET_EXTENSION))
			.toString();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("path", path);
		params.put("user", user);

		List<User> recipients = new ArrayList<User>();
		recipients.add(user);

		this.setUsers(recipients);
		this.setTemplateParams(params);

		super.prepareMessage(this.getConfig().getValue(ConfigKeys.MAIL_NEW_PM_SUBJECT),
			this.getConfig().getValue(ConfigKeys.MAIL_NEW_PM_MESSAGE_FILE));
	}
}