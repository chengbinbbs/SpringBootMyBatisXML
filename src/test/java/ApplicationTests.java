import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.daysluck.Application;
import com.daysluck.domain.User;
import com.daysluck.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class ApplicationTests {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RedisTemplate<String, User> redisTemplate;

	
	@Test
	@Rollback
	public void findByName() throws Exception {
		userMapper.insertByUser(new User("AAA",23));
		User u = userMapper.findByName("AAA");
		Assert.assertEquals(20, u.getAge().intValue());
	}
	
	@Test
	public void test() throws Exception {
		// 保存对象
		User user = new User("超人", 20);
		redisTemplate.opsForValue().set(user.getName(), user);
		user = new User("蝙蝠侠", 30);
		redisTemplate.opsForValue().set(user.getName(), user);
		user = new User("蜘蛛侠", 40);
		redisTemplate.opsForValue().set(user.getName(), user);
		Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().longValue());
		Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
		Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());
	}
}
