package xft.workbench.backstage.stock.biz;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xft.workbench.backstage.stock.dao.StockDao;


@Service
public class StockService {
	
	@Autowired
	private StockDao stockDao;
	
	
}
