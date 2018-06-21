package com.migu.schedule;

import com.migu.schedule.constants.ReturnCodeKeys;
import com.migu.schedule.info.TaskInfo;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/*
*类名和方法不能修改
 */
public class Schedule {

	LinkedHashMap<Integer, Integer> queue = new LinkedHashMap<Integer, Integer>();// 队列
	Map<Integer, TaskInfo> service = new HashMap<Integer, TaskInfo>();// 服务

	/**
	 * 信息初始化
	 * 
	 * @return
	 */
	public int init() {
		try {
			// 任务信息初始化
			queue = new LinkedHashMap<Integer, Integer>();
			service = new HashMap<Integer, TaskInfo>();
			return ReturnCodeKeys.E001;
		} catch (Exception e) {
			return ReturnCodeKeys.E000;
		}
	}

	/**
	 * 服务节点注册
	 * 
	 * @param nodeId
	 * @return
	 */
	public int registerNode(int nodeId) {
		if (nodeId <= 0) {
			return ReturnCodeKeys.E004;// 服务节点编号非法。
		}
		if (service.get(nodeId) != null) {
			return ReturnCodeKeys.E005;// 服务节点已注册
		}
		try {
			TaskInfo taskInfo = new TaskInfo();
			taskInfo.setNodeId(nodeId);
			service.put(nodeId, taskInfo);
			return ReturnCodeKeys.E003;

		} catch (Exception e) {
			return ReturnCodeKeys.E000;// 异常处理
		}
	}

	/**
	 * 服务节点注销
	 * 
	 * @param nodeId
	 * @return
	 */
	public int unregisterNode(int nodeId) {
		if (nodeId <= 0) { 
			return ReturnCodeKeys.E004;// 服务节点编号非法
		}
		if (service.get(nodeId) == null) {
			return ReturnCodeKeys.E007;// 服务节点不存在
		}
		if (service.get(nodeId).getTaskId() > 0) {
			queue.put(nodeId, 0);/** ----???---- */
			return ReturnCodeKeys.E006;// 服务节点注销成功
		} else {
			service.remove(nodeId);
			return ReturnCodeKeys.E006;// 服务节点注销成功
		}
	}

	/**
	 * 添加任务
	 * 
	 * @param taskId
	 * @param consumption
	 * @return
	 */
	public int addTask(int taskId, int consumption) {
		if (taskId <= 0) {
			return ReturnCodeKeys.E009;// 任务编号非法
		}
		if (queue.containsKey(taskId)) {
			return ReturnCodeKeys.E010;// 任务已被添加
		}
		queue.put(taskId, consumption);// 任务添加成功
		return ReturnCodeKeys.E008;
	}

	/**
	 * 删除任务
	 * 
	 * @param taskId
	 * @return
	 */
	public int deleteTask(int taskId) {
		if (taskId <= 0) {
			return ReturnCodeKeys.E009;// 任务编号非法
		}
		if (!queue.containsKey(taskId)) {
			return ReturnCodeKeys.E012;// 任务不存在
		}
		TaskInfo taskInfo = new TaskInfo();
		taskInfo.setTaskId(taskId);
		// 删除/对列
		for (int i = 0; i < queue.size(); i++) {
			if (queue.get(i) == taskId) {
				queue.remove(taskId);
				break;
			}
		}
		// 删除/服务
		for (Entry<Integer, TaskInfo> iterable_element : service.entrySet()) {
			if (iterable_element.getValue().getTaskId() == taskId) {
				service.remove(iterable_element.getKey());
				break;
			}
		}
		return ReturnCodeKeys.E011;// 任务删除成功
	}

	public int scheduleTask(int threshold) {
		// TODO 方法未实现
		return ReturnCodeKeys.E000;
	}

	public int queryTaskStatus(List<TaskInfo> tasks) {
		// TODO 方法未实现
		return ReturnCodeKeys.E000;
	}

}
