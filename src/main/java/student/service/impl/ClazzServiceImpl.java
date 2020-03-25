package student.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.dao.ClazzDao;
import student.pojo.Clazz;
import student.service.ClazzService;

import java.util.List;
import java.util.Map;

/**
 * ClassName: ClazzServiceImpl
 * Package: student.service.impl
 * Date: 2020/2/13 12:24
 * Author; 吴华川
 */
@Service
public class ClazzServiceImpl implements ClazzService {
    @Autowired
    private ClazzDao clazzDao;

    @Override
    public int add(Clazz clazz) {
        return clazzDao.add(clazz);
    }

    @Override
    public int edit(Clazz clazz) {
        return clazzDao.edit(clazz);
    }

    @Override
    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    @Override
    public List<Clazz> findList(Map<String, Object> queryMap) {

        return clazzDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return clazzDao.getTotal(queryMap);
    }

    @Override
    public List<Clazz> findAll() {

        return clazzDao.findAll();
    }

}
