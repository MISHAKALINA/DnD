package org.dnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RollService {

    public static int rolling(int amount, int type, int modify, boolean each) {

        Random random = new Random();
        int total = 0;
        for (int i = 0; i < amount; i++) {
            int roll = random.nextInt(type) + 1;
            total += roll;
        }
        if (each){
            total+=modify;
        }
        else {
            total+=modify*amount;
        }
        return total;

    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void deleteOldRolls() {
        String sql = """
                WITH ranked_data AS (
                  SELECT *,
                         ROW_NUMBER() OVER (PARTITION BY username ORDER BY id ASC) AS row_num
                  FROM rolls
                )
                DELETE FROM rolls
                WHERE id IN (
                  SELECT id
                  FROM ranked_data
                  WHERE row_num = 1 AND username IN (
                    SELECT username
                    FROM rolls
                    GROUP BY username
                    HAVING COUNT(*) > 10
                  )
                )""";
        jdbcTemplate.execute(sql);
    }
}