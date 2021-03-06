package com.vedri.mtp.processor.streaming.handler;

import com.vedri.mtp.core.MtpConstants;
import com.vedri.mtp.core.transaction.TableName;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.joda.time.DateTime;

import com.google.common.collect.Sets;
import com.vedri.mtp.core.transaction.Transaction;
import com.vedri.mtp.core.transaction.aggregation.TransactionAggregationByCurrency;

import java.math.BigDecimal;

public class ReceivedTimeTransactionAggregationByCurrencyBuilder
		extends TimeTransactionAggregationByCurrencyBuilderTemplate {

	public ReceivedTimeTransactionAggregationByCurrencyBuilder(StreamBuilder<?, JavaDStream<Transaction>> prevBuilder,
			String keyspace) {
		super(prevBuilder, keyspace, TableName.RT_AGGREGATION_BY_CURRENCY);
	}

	@Override
	protected FlatMapFunction<Transaction, TransactionAggregationByCurrency> mapFunction() {
		return transaction -> {
			final DateTime time = transaction
					.getReceivedTime()
					.withZone(MtpConstants.DEFAULT_TIME_ZONE);
			return Sets.newHashSet(
					new TransactionAggregationByCurrency(transaction.getCurrencyFrom(),
							time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(),
							1, 0, transaction.getAmountSell(), BigDecimal.ZERO),
					new TransactionAggregationByCurrency(transaction.getCurrencyTo(),
							time.getYear(), time.getMonthOfYear(), time.getDayOfMonth(), time.getHourOfDay(),
							0, 1, BigDecimal.ZERO, transaction.getAmountBuy()));
		};
	}
}
