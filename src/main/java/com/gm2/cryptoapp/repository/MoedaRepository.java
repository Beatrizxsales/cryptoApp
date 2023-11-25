package com.gm2.cryptoapp.repository;

import com.gm2.cryptoapp.dto.MoedaTransationDTO;
import com.gm2.cryptoapp.entity.Moeda;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class MoedaRepository {

    private static String INSERT = "insert into moeda(nome, preco, quantidade, data) values (?,?,?,?)";

    private static String SELECT_ALL = "select nome, sum(quantidade) as quantidade from moeda group by nome";

    private static String SELECT_BY_NAME = "select * from moeda where nome = ?";

    private static String DELETE = "delete from moeda where id = ?";

    private static String UPDATE = "update moeda set nome = ?, preco = ?, quantidade = ? where id = ?";

    private JdbcTemplate jdbcTemplate;

    public MoedaRepository(JdbcTemplate jdbcTemplate){ this.jdbcTemplate = jdbcTemplate;}

    public Moeda insert (@org.jetbrains.annotations.NotNull Moeda moeda){
        Object[] atrr = new Object[]{
                moeda.getNome(),
                moeda.getPreco(),
                moeda.getQuantidade(),
                moeda.getDateTime()
        };
        jdbcTemplate.update(INSERT,atrr);
        return moeda;
    }

    public Moeda update(Moeda moeda){
        Object[] atrr = new Object[]{
                moeda.getNome(),
                moeda.getPreco(),
                moeda.getQuantidade(),
                moeda.getId()
        };
        jdbcTemplate.update(UPDATE,atrr);
        return moeda;
    }
    public List<MoedaTransationDTO> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<MoedaTransationDTO>() {
            @Override
            public MoedaTransationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MoedaTransationDTO moeda= new MoedaTransationDTO();
                moeda.setNome(rs.getString("nome"));
                moeda.setQuantidade(rs.getBigDecimal("quantidade"));
                return moeda;
            }
        });
    }

    public List<Moeda> getByName( String nome){
        Object[] atrr = new Object[]{ nome };
        return jdbcTemplate.query(SELECT_BY_NAME, new RowMapper<Moeda>() {
            @Override
            public Moeda mapRow(ResultSet rs, int rowNum) throws SQLException {
                Moeda moeda = new Moeda();
                moeda.setId(rs.getInt("id"));
                moeda.setNome(rs.getString("nome"));
                moeda.setPreco(rs.getBigDecimal("preco"));
                moeda.setQuantidade(rs.getBigDecimal("quantidade"));
                moeda.setDateTime(rs.getTimestamp("data"));

                return moeda;
            }
        },atrr);
    }

    public int remove(int id){
        return jdbcTemplate.update(DELETE,id);
    }
}
